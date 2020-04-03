{-# LANGUAGE FlexibleInstances, OverloadedStrings, BangPatterns #-}

module Language.Nano.TypeCheck where

import Language.Nano.Types
import Language.Nano.Parser

import qualified Data.List as L
import           Text.Printf (printf)  
import           Control.Exception (throw)

--------------------------------------------------------------------------------
typeOfFile :: FilePath -> IO Type
typeOfFile f = readFile f >>= typeOfString

typeOfString :: String -> IO Type
typeOfString s = typeOfExpr (parseExpr s)

typeOfExpr :: Expr -> IO Type
typeOfExpr e = do
  let (!st, t) = infer initInferState preludeTypes e
  if (length (stSub st)) < 0 then throw (Error ("count Negative: " ++ show (stCnt st)))
  else return t

--------------------------------------------------------------------------------
-- Problem 1: Warm-up
--------------------------------------------------------------------------------

-- | Things that have free type variables
class HasTVars a where
  freeTVars :: a -> [TVar]

-- | Type variables of a type
instance HasTVars Type where
  freeTVars TInt      = []
  freeTVars TBool     = []
  freeTVars (a :=> b) = L.nub ((freeTVars a) ++ (freeTVars b))
  freeTVars (TVar x)    = [x]
  freeTVars (TList x)   = L.nub (freeTVars x)
  
-- | Free type variables of a poly-type (remove forall-bound vars)
instance HasTVars Poly where
  freeTVars (Mono x)            = freeTVars x
  freeTVars (Forall v p)        = L.nub (L.delete v (freeTVars p))

-- | Free type variables of a type environment
instance HasTVars TypeEnv where
  freeTVars gamma   = concat [freeTVars s | (x, s) <- gamma]  
  
-- | Lookup a variable in the type environment  
lookupVarType :: Id -> TypeEnv -> Poly
lookupVarType x ((y, s) : gamma)
  | x == y    = s
  | otherwise = lookupVarType x gamma
lookupVarType x [] = throw (Error ("unbound variable: " ++ x))

-- | Extend the type environment with a new biding
extendTypeEnv :: Id -> Poly -> TypeEnv -> TypeEnv
extendTypeEnv x s gamma = (x,s) : gamma  

-- | Lookup a type variable in a substitution;
--   if not present, return the variable unchanged
lookupTVar :: TVar -> Subst -> Type
lookupTVar a []     = TVar a
lookupTVar a (x:xs)
   | fst x == a     = snd x
   | otherwise      = lookupTVar a xs

-- | Remove a type variable from a substitution
removeTVar :: TVar -> Subst -> Subst
removeTVar a sub
   | x == TVar a     = sub
   | otherwise       = L.delete (a, x) sub
      where
         x = lookupTVar a sub
     
-- | Things to which type substitutions can be apply
class Substitutable a where
  apply :: Subst -> a -> a
  
-- | Apply substitution to type
instance Substitutable Type where  
  apply sub TInt      = TInt
  apply sub TBool     = TBool
  apply sub (a :=> b) = (a' :=> b') 
     where
        a'            = (apply sub a)
        b'            = (apply sub b)
  apply sub (TVar x)  = (lookupTVar x sub)
  apply sub (TList x) = TList (apply sub x)
  
-- | Apply substitution to poly-type
instance Substitutable Poly where    
  apply sub (Mono s)     = Mono (apply sub s)
  apply sub (Forall v p) = Forall v (apply (removeTVar v sub) p)

-- | Apply substitution to (all poly-types in) another substitution
instance Substitutable Subst where  
  apply sub to = zip keys $ map (apply sub) vals
    where
      (keys, vals) = unzip to
      
-- | Apply substitution to a type environment
instance Substitutable TypeEnv where  
  apply sub gamma = zip keys $ map (apply sub) vals
    where
      (keys, vals) = unzip gamma
      
-- | Extend substitution with a new type assignment
extendSubst :: Subst -> TVar -> Type -> Subst
extendSubst sub a t = (a, t) : (apply [(a,t)] sub)

--------------------------------------------------------------------------------
-- Problem 2: Unification
--------------------------------------------------------------------------------
      
-- | State of the type inference algorithm      
data InferState = InferState { 
    stSub :: Subst -- ^ current substitution
  , stCnt :: Int   -- ^ number of fresh type variables generated so far
} deriving Show

-- | Initial state: empty substitution; 0 type variables
initInferState = InferState [] 0

-- | Fresh type variable number n
freshTV n = TVar $ "a" ++ show n      
    
-- | Extend the current substitution of a state with a new type assignment   
extendState :: InferState -> TVar -> Type -> InferState
extendState (InferState sub n) a t = InferState (extendSubst sub a t) n
        
-- | Unify a type variable with a type; 
--   if successful return an updated state, otherwise throw an error
unifyTVar :: InferState -> TVar -> Type -> InferState
unifyTVar st a TInt      = extendState st a TInt
unifyTVar st a TBool     = extendState st a TBool
unifyTVar st a (b :=> c)
   | a == show b && a == show c    = error "type error: cannot unify a and (a -> a)"
   | a == show c                   = error "type error: cannot unify a and (b -> a)" -- added this idk if i need it
   |otherwise                      = extendState st a (b :=> c)  
unifyTVar st a (TVar b)  
   | a == b              = st
   |otherwise            = extendState st a (TVar b)
unifyTVar st a (TList b) 
   | a == show b         = error "type error: cannot unify a and [a]" 
   |otherwise            = extendState st a (list b)
    
-- | Unify two types;
--   if successful return an updated state, otherwise throw an error
unify :: InferState -> Type -> Type -> InferState
unify st TInt TInt           = st
unify st TInt TBool          = error "type error: cannot unify TInt and TBool"
unify st TInt (a :=> b)      = error "type error: cannot unify TInt and (Type :=> Type)"
unify st TInt (TVar x)       = unifyTVar st x TInt
unify st TInt (TList x)      = error "type error: cannot unify TInt and (TList x)"
unify st TBool TInt          = error "type error: cannot unify TBool and TInt"
unify st TBool TBool         = st
unify st TBool (a :=> b)     = error "type error: cannot unify TBool and (Type :=> Type)"
unify st TBool (TVar x)      = unifyTVar st x TBool
unify st TBool (TList x)     = error "type error: cannot unify TBool and (TList x)"
unify st (a :=> b) TInt      = error "type error: cannot unify (a :=> b) and TInt"
unify st (a :=> b) TBool     = error "type error: cannot unify (a :=> b) and TBool"
unify st (a :=> b) (c :=> d)
   | a == c && b == d        = st
   | a == b                  = unify (unify st c d) a c -- added this idk why
   | otherwise               = unify (unify st a c) b d
unify st (a :=> b) (TVar x ) = unifyTVar st x (a :=> b)
unify st (a :=> b) (TList x) = error "type error: cannot unify (a :=> b) and (TList x)"
unify st (TVar x) TInt       = unifyTVar st x TInt
unify st (TVar x) TBool      = unifyTVar st x TBool
unify st (TVar x) (a :=> b)  = unifyTVar st x (a :=> b)
unify st (TVar x) (TVar y)   = unifyTVar st x (TVar y)
unify st (TVar x) (TList y)  = unifyTVar st x (TList y)
unify st (TList x) (TInt)    = error "type error: cannot unify (TList x) and TInt"
unify st (TList x) (TBool)   = error "type error: cannot unify (TList x) and TBool"
unify st (TList x) (a :=> b) = error "type error: cannot unify (TList x) and (a :=> b)"
unify st (TList x) (TVar y)  = unifyTVar st y (TList x)
unify st (TList x) (TList y)
   | x == y                  = st
   |otherwise                = unify st x y

--------------------------------------------------------------------------------
-- Problem 3: Type Inference
--------------------------------------------------------------------------------    
  
infer :: InferState -> TypeEnv -> Expr -> (InferState, Type)
infer st _   (EInt _)          = (st, TInt)
infer st _   (EBool _)         = (st, TBool)
infer st gamma (EVar x)        = (st, snd (instantiate (stCnt st) (lookupVarType x gamma)))
infer st gamma (ELam x body)   = (st, (x' :=> body'))
                                  where
                                     gamma' = extendTypeEnv x (Mono x') gamma
                                     x'     = freshTV (stCnt st)
                                     body'  = snd (infer st gamma' body)
infer st gamma (EApp e1 e2)    = ((unify st (snd e1') newType), res)
                                    where
                                       e1'  = infer st gamma e1
                                       e2'  = infer st gamma e2
                                       newType = ((snd e2') :=> (freshTV (stCnt st)))
                                       ftv     = (freshTV (stCnt st))
                                       res     = lookupTVar (show ftv) (stSub (unify st (snd e1') newType))
                                       
infer st gamma (ELet x e1 e2)  = error "TBD: ELet"
--                               blah
--                                   where
--                                     x'      = freshTV (stCnt st)
--                                     gamma'  = extendTypeEnv x ge1' gamma
--                                     e1'     = infer st gamma e1
--                                     e2'     = infer st gamma' e2
--                                     ge1'     = generalize st (snd e1')
infer st gamma (EBin op e1 e2) = infer st gamma asApp
  where
    asApp = EApp (EApp opVar e1) e2
    opVar = EVar (show op)
infer st gamma (EIf c e1 e2) = infer st gamma asApp
  where
    asApp = EApp (EApp (EApp ifVar c) e1) e2
    ifVar = EVar "if"    
infer st gamma ENil = infer st gamma (EVar "[]")

-- | Generalize type variables inside a type
generalize :: TypeEnv -> Type -> Poly
generalize gamma t = blah vars
                   where
                      vars           = L.nub ((freeTVars t) L.\\ (L.nub (freeTVars gamma))) -- list of the free variables in t but NOT in gamma
                      blah []        = Mono t
                      blah (x:xs)    = Forall x (blah xs)
-- | Instantiate a polymorphic type into a mono-type with fresh type variables
instantiate :: Int -> Poly -> (Int, Type)
instantiate n (Mono m)     = (n, m)
instantiate n (Forall v p) = instantiate (n+1) (apply [(v, (freshTV n))] p)
      
-- | Types of built-in operators and functions      
preludeTypes :: TypeEnv
preludeTypes =
  [ ("+",    Mono $ TInt :=> TInt :=> TInt)
  , ("-",    error "TBD: -")
  , ("*",    error "TBD: *")
  , ("/",    error "TBD: /")
  , ("==",   error "TBD: ==")
  , ("!=",   error "TBD: !=")
  , ("<",    error "TBD: <")
  , ("<=",   error "TBD: <=")
  , ("&&",   error "TBD: &&")
  , ("||",   error "TBD: ||")
  , ("if",   error "TBD: if")
  -- lists: 
  , ("[]",   error "TBD: []")
  , (":",    error "TBD: :")
  , ("head", error "TBD: head")
  , ("tail", error "TBD: tail")
  ]
