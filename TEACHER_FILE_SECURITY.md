# 🔒 Fichiers Professeur - Guide de sécurité

## 📋 Contexte

Ce projet contient des fichiers pédagogiques destinés **uniquement aux professeurs/moniteurs**:

- ✅ `SOLUTION.md` - Réponses complètes aux exercices
- ✅ `QUICK_REFERENCE.md` - Feuille de correction

**Objectif** : Empêcher les étudiants de les voir directement dans le Git.

---

## 🔐 Stratégie

### État initial (avant commit)
```
SOLUTION.md              ← Visible
QUICK_REFERENCE.md       ← Visible (optionnel)
.gitignore               ← Ces fichiers sont ignorés
```

### État final (après sécurisation)
```
SOLUTION.md.enc          ← Chiffré (visible à tous)
QUICK_REFERENCE.md.enc   ← Chiffré (optionnel)
.gitignore               ← SOLUTION.md et QUICK_REFERENCE.md ajoutés
manage_teacher_files.sh  ← Script de déchiffrement (visible)
```

---

## 🚀 Comment mettre en place

### Étape 1: Rendre le script exécutable
```bash
chmod +x manage_teacher_files.sh
```

### Étape 2: Chiffrer le fichier SOLUTION.md
```bash
./manage_teacher_files.sh encrypt "votre_mot_de_passe_securise_ici"
```

**Output:**
```
🔐 Chiffrement de SOLUTION.md...
✅ Fichier chiffré: SOLUTION.md.enc
📝 Vous pouvez maintenant supprimer SOLUTION.md avec: rm SOLUTION.md
```

### Étape 3: Supprimer le fichier original
```bash
rm SOLUTION.md
ls -la SOLUTION.md.enc  # Vérifier que le chiffré existe
```

### Étape 4: Ajouter à .gitignore
```bash
echo "SOLUTION.md" >> .gitignore
echo "QUICK_REFERENCE.md" >> .gitignore
```

### Étape 5: Commiter les changements
```bash
git add manage_teacher_files.sh SOLUTION.md.enc .gitignore
git commit -m "feat: ajouter système de sécurité pour fichiers prof"
git push
```

### Étape 6 (optionnel): Chiffrer aussi QUICK_REFERENCE.md
```bash
./manage_teacher_files.sh encrypt "même_mot_de_passe"
rm QUICK_REFERENCE.md
git add QUICK_REFERENCE.md.enc
git commit -m "feat: chiffrer QUICK_REFERENCE.md"
```

---

## 🔑 Distribution du mot de passe aux profs

Le mot de passe doit être partagé **SÉPARÉMENT** du Git:

**Canaux sécurisés possibles:**
- Email chiffré
- Gestionnaire de mots de passe partagé (LastPass, 1Password, Bitwarden)
- Plateforme pédagogique protégée (Moodle, Canvas)
- Communication directe sécurisée

**❌ NE PAS mettre le mot de passe dans:**
- Le code Git
- Le README public
- Les commentaires

---

## 🔓 Utilisation par les profs

1. **Réception du repo Git**
   ```bash
   git clone <repo>
   cd simple-oauth
   ```

2. **Réception du mot de passe** (par canal sécurisé)

3. **Déchiffrer le fichier**
   ```bash
   ./manage_teacher_files.sh decrypt "mot_de_passe_recu"
   ```

4. **Le fichier SOLUTION.md est maintenant visible**
   ```bash
   cat SOLUTION.md
   ```

---

## 🛡️ Sécurité

### Ce système protège contre:
✅ Les étudiants qui voient le fichier sur GitHub
✅ Les curiosités accidentelles
✅ Les copies de repo sans autorisation

### Ce système NE protège PAS contre:
❌ Un prof qui partage le mot de passe
❌ Un attaquant qui force le mot de passe
❌ Un étudiant qui a accès au terminal du prof

### Pour plus de sécurité:
- Utiliser un mot de passe **très fort** (>20 caractères, mélange)
- Changer le mot de passe régulièrement
- Utiliser des clés publique/privée (GPG) si possible

---

## 🧪 Test du système

```bash
# 1. Chiffrer
./manage_teacher_files.sh encrypt "TestPassword123!"

# 2. Vérifier que le fichier chiffré existe
ls -la SOLUTION.md.enc

# 3. Supprimer l'original
rm SOLUTION.md

# 4. Déchiffrer
./manage_teacher_files.sh decrypt "TestPassword123!"

# 5. Vérifier que le contenu est intègre
cat SOLUTION.md
```

---

## 📋 Checklist avant commit

- [ ] `SOLUTION.md` est chiffré en `SOLUTION.md.enc`
- [ ] `SOLUTION.md` original est supprimé
- [ ] `.gitignore` contient `SOLUTION.md`
- [ ] `manage_teacher_files.sh` est présent et exécutable
- [ ] `git status` ne montre pas de fichier prof à commiter
- [ ] Le mot de passe est stocké de manière sécurisée (PAS en Git!)
- [ ] Les profs ont reçu le mot de passe par canal sécurisé

---

## 🆘 Troubleshooting

### "Mot de passe incorrect" lors du déchiffrement
→ Vérifier que le mot de passe est exact
→ Vérifier que `SOLUTION.md.enc` n'a pas été corrompu

### "Fichier not found"
→ Vérifier que vous êtes dans le bon répertoire
→ Vérifier que `SOLUTION.md.enc` existe

### "Permission denied" sur le script
→ Exécuter: `chmod +x manage_teacher_files.sh`

---

## 🔄 Workflow recommandé

**Pour les profs pendant le cours:**

```bash
# En début de session (avoir le fichier déchiffré)
./manage_teacher_files.sh decrypt "password"

# Utiliser SOLUTION.md pour corriger
# ...

# À la fin (resécuriser avant de fermer)
rm SOLUTION.md
git status  # Vérifier qu'il ne traîne pas
```

**Pour les étudiants:**
```bash
# Ils ne voient JAMAIS les fichiers prof
git clone <repo>
ls -la SOLUTION.md  # Ne existe pas
ls -la SOLUTION.md.enc  # Existe mais chiffré (inutile)
```

---

## 🎓 Alternative: Branches séparées

Si vous préférez une approche avec **branches GitHub**:

```bash
# Créer une branche instructors-only
git checkout -b instructors-only
# Ajouter les fichiers prof
git push origin instructors-only

# Protéger la branche (sur GitHub)
Settings → Branches → Add rule
  - Pattern: instructors-only
  - Require pull request reviews: Yes
  - Restrict who can push: Only Admins
```

Avantages: Pas besoin de chiffrement
Inconvénients: Nécessite GitHub, plus visible, gestion plus complexe

---

## 📖 Ressources

- [OpenSSL enc man page](https://www.openssl.org/docs/manmaster/man1/openssl-enc.html)
- [Git Ignore patterns](https://git-scm.com/docs/gitignore)
- [CODEOWNERS GitHub](https://docs.github.com/en/repositories/managing-your-repositorys-settings-and-features/customizing-your-repository/about-code-owners)

---

## ✅ Résumé

Une seule commande pour les profs:
```bash
./manage_teacher_files.sh decrypt "password"
```

Et le fichier est visible 🔓

Assez simple pour le workflow, assez sécurisé pour les étudiants.
