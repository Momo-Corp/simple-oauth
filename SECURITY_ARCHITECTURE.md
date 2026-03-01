# 🔐 Système de sécurité - Diagramme d'accès

## Architecture avant (❌ Non sécurisé)

```
GitHub Repo
├── SOLUTION.md              ← ⚠️ TOUS VOIENT
├── QUICK_REFERENCE.md       ← ⚠️ TOUS VOIENT
├── SECURITY_EXERCISE.md     ← OK (public)
└── ...

Étudiant:
  git clone
  → cat SOLUTION.md           ← Voit les réponses! 😱
```

---

## Architecture après (✅ Sécurisé)

```
GitHub Repo
├── SOLUTION.md.enc          ← 🔐 Chiffré (personne ne peut lire)
├── QUICK_REFERENCE.md.enc   ← 🔐 Chiffré (personne ne peut lire)
├── manage_teacher_files.sh  ← Script déchiffrement (visible)
├── TEACHER_FILE_SECURITY.md ← Documentation (visible)
├── TEACHER_QUICKSTART.md    ← Guide rapide (visible)
├── SECURITY_EXERCISE.md     ← OK (public)
└── .gitignore
    ├── SOLUTION.md          ← ✅ Jamais commité
    └── QUICK_REFERENCE.md   ← ✅ Jamais commité

Étudiant:
  git clone
  → ls -la SOLUTION.md        ← N'existe pas ❌
  → cat SOLUTION.md.enc       ← Données aléatoires 🔒
  
Professeur:
  git clone
  → ./manage_teacher_files.sh decrypt "password"
  → cat SOLUTION.md           ← Voit les réponses ✅
```

---

## Workflows de sécurité

### Workflow Prof: Création

```
1️⃣  Écrire SOLUTION.md
    ↓
2️⃣  Chiffrer
    ./manage_teacher_files.sh encrypt "SecurePass123"
    ↓
3️⃣  Supprimer original
    rm SOLUTION.md
    ↓
4️⃣  Commiter chiffré
    git add SOLUTION.md.enc manage_teacher_files.sh .gitignore
    git commit -m "🔒 sécuriser fichiers prof"
    git push
    ↓
5️⃣  Partager mot de passe SÉPARÉMENT
    (Email chiffré, Moodle, communication directe)
```

### Workflow Prof: Utilisation

```
1️⃣  Recevoir le repo
    git clone ...
    ↓
2️⃣  Recevoir le mot de passe
    (par canal sécurisé)
    ↓
3️⃣  Déchiffrer
    ./manage_teacher_files.sh decrypt "SecurePass123"
    ↓
4️⃣  Accéder aux fichiers
    cat SOLUTION.md
    cat QUICK_REFERENCE.md
    ↓
5️⃣  Utiliser pour corriger (ou finir)
    ↓
6️⃣  Resécuriser avant de fermer
    rm SOLUTION.md QUICK_REFERENCE.md
    git status  # Vérifier qu'il ne traîne pas
```

### Workflow Étudiant: Cloner

```
1️⃣  Cloner le repo
    git clone ...
    ↓
2️⃣  Voir les fichiers publics
    cat SECURITY_EXERCISE.md ✅
    ✅ Les tests
    ✅ Les guides
    ❌ Les réponses (chiffré)
    ↓
3️⃣  Faire les exercices
    Modifier le code Spring...
    ↓
4️⃣  Lancer les tests
    pytest tests/test_security_exercise.py -v
    ↓
5️⃣  Si bloqué: lire les guides, pas les réponses
```

---

## État des fichiers à chaque étape

### Avant commit (côté développeur/prof)

```
Step 1 - Création:
  SOLUTION.md           ← Fichier brut (très sensible)
  .gitignore            ← Ajouté (ignore SOLUTION.md)

Step 2 - Chiffrement:
  SOLUTION.md           ← Original (va être supprimé)
  SOLUTION.md.enc       ← Chiffré (va être commité)

Step 3 - Nettoyage:
  SOLUTION.md.enc       ← Chiffré ✅
  SOLUTION.md           ← ❌ Supprimé
  
Step 4 - Git:
  git add SOLUTION.md.enc .gitignore manage_teacher_files.sh
  git commit
  ✅ SOLUTION.md.enc → GitHub
  ❌ SOLUTION.md → JAMAIS GitHub
  ✅ manage_teacher_files.sh → GitHub
```

### Après clone (côté étudiant)

```
Student:
  SOLUTION.md           ← ❌ N'existe pas (dans .gitignore)
  SOLUTION.md.enc       ← ✅ Existe mais INUTILE 🔐
  manage_teacher_files.sh → ✅ Existe (pour info)
```

### Après clone (côté prof)

```
Prof - Avant déchiffrement:
  SOLUTION.md           ← ❌ N'existe pas
  SOLUTION.md.enc       ← ✅ Existe

Prof - Après déchiffrement:
  SOLUTION.md           ← ✅ Déchiffré (pas commité)
  SOLUTION.md.enc       ← ✅ Original chiffré
  
Prof - Avant de partir:
  rm SOLUTION.md        ← Supprimer pour ne pas l'oublier
  git status            ← Vérifier que rien ne traîne
```

---

## Sécurité: Niveaux de protection

| Niveau | Protection | Technologie |
|--------|-----------|-------------|
| 🔴 Niveau 0 | Aucune | Fichier visible en Git |
| 🟠 Niveau 1 | Basique | .gitignore seul |
| 🟡 Niveau 2 | Bonne | .gitignore + chiffrement symétrique |
| 🟢 Niveau 3 | Strong | Branches protégées + GPG |
| 🟢🟢 Niveau 4 | Military | Chiffrement asymétrique + clés privées |

**Ce projet = Niveau 2 (bon pour un contexte éductif)**

---

## Matrices d'accès

### Fichier: SOLUTION.md.enc

```
          Peut voir    Peut déchiffrer
Étudiant    ❌ (en Git)      ❌ (pas de pwd)
Prof        ✅ (en Git)      ✅ (a le pwd)
Public      ❌ (pas de repo) ❌ (pas de pwd)
```

### Fichier: manage_teacher_files.sh

```
          Peut voir    Peut utiliser
Étudiant    ✅         ⚠️  (sans mot de passe)
Prof        ✅         ✅  (avec mot de passe)
Public      ✅         ❌  (sans mot de passe)
```

### Fichier: SECURITY_EXERCISE.md

```
          Peut voir
Étudiant    ✅ (objectif!)
Prof        ✅
Public      ✅ (si repo public)
```

---

## Exemple: Git History

```bash
$ git log --oneline

abc1234 🔒 sécuriser fichiers prof
def5678 Add exercises and guides
ghi9012 Initial commit

$ git show abc1234

commit abc1234
    - SOLUTION.md.enc ← AJOUTÉ
    + SOLUTION.md    ← RETIRÉ
    ~ manage_teacher_files.sh
    ~ .gitignore
```

Personne ne peut voir SOLUTION.md dans l'historique (jamais commité) ✅

---

## Modèle de menace

### Menacequ'il défend ✅

| Menace | Défense |
|--------|---------|
| Étudiant clone et lit réponses | `SOLUTION.md` jamais en Git |
| Étudiant regarde le `git log` | Jamais commité, aucune trace |
| Curieux regarde GitHub publiquement | Juste binaries chiffré, inutile |
| Quelqu'un accède au repo mais pas à pwd | Fichier enc inutilisable |

### Menaces qu'il ne défend PAS ❌

| Menace | Pourquoi |
|--------|----------|
| Prof partage le mot de passe | Décision humaine, pas tech |
| Étudiant accède au terminal du prof | Accès physique, pas de solution tech |
| Brute-force du mot de passe | Mot de passe faible → choix utilisateur |
| Professionnel adversaire très motivé | OpenSSL peut être casséé avec ressources |

---

## Résumé visuel: Avant/Après

```
AVANT (❌ Pas sécurisé)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    ↓ git clone
┌───────────────────┐
│ Étudiant (Git)    │
│                   │
│ SOLUTION.md ←─────┼─ 😱 VOIT TOUT!
│                   │
└───────────────────┘

APRÈS (✅ Sécurisé)
━━━━━━━━━━━━━━━━━━━━━━━━━━━━━
    ↓ git clone
┌────────────────────────────┐
│ Étudiant (Git)             │
│                            │
│ SOLUTION.md ← N'existe pas │
│ SOLUTION.md.enc ← 🔐      │
│                            │
└────────────────────────────┘
         ↓ Il voudrait voir...
         ↓ Mais besoin du mot de passe
         ↙ Pas en Git, doit le demander
      ❌ Accès refusé

    ↓ git clone
┌────────────────────────────┐
│ Prof (Git + mot passe)     │
│                            │
│ SOLUTION.md.enc ← 🔐      │
│ ./manage...sh              │
│                            │
│ ./manage... decrypt "pwd"  │
│      ↓                     │
│ SOLUTION.md ← ✅ DÉCHIFFRÉ│
│                            │
└────────────────────────────┘
```
