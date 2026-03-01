# ✅ Checklist de déploiement - Système de sécurité prof

## Phase 1: Préparation
- [ ] Les fichiers `SOLUTION.md` et `QUICK_REFERENCE.md` sont finalisés
- [ ] Ils sont complets et testés
- [ ] Le mot de passe sécurisé a été généré
  Example: `openssl rand -base64 32` → `abc123XYZ...` (>20 chars)
- [ ] J'ai noté le mot de passe en lieu SÛR (gestionnaire de mots de passe)

---

## Phase 2: Chiffrement
- [ ] Le script `manage_teacher_files.sh` est présent
- [ ] Le script est exécutable: `chmod +x manage_teacher_files.sh`
- [ ] Exécuter: `./manage_teacher_files.sh encrypt "VOTRE_MOT_DE_PASSE"`
  
  Vérifier l'output:
  ```
  🔐 Chiffrement de SOLUTION.md...
  ✅ Fichier chiffré: SOLUTION.md.enc
  ```

- [ ] Appliquer aussi sur QUICK_REFERENCE.md si nécessaire
  `./manage_teacher_files.sh encrypt "VOTRE_MOT_DE_PASSE"`

---

## Phase 3: Nettoyage
- [ ] Supprimer le fichier original: `rm SOLUTION.md`
- [ ] Supprimer aussi: `rm QUICK_REFERENCE.md` (si chiffré)
- [ ] Vérifier qu'ils n'existent plus:
  ```bash
  ls -la SOLUTION.md           # ❌ error: No such file
  ls -la SOLUTION.md.enc       # ✅ exists
  ```

---

## Phase 4: Git Configuration
- [ ] Vérifier que `.gitignore` ignore ces fichiers:
  ```bash
  cat .gitignore | grep SOLUTION.md
  # Doit afficher: SOLUTION.md
  ```
- [ ] Si pas lá, ajouter: `echo "SOLUTION.md" >> .gitignore`
- [ ] Vérifier le status Git:
  ```bash
  git status
  # Doit montrer:
  #   modified:   .gitignore
  #   new file:   manage_teacher_files.sh
  #   new file:   SOLUTION.md.enc
  # (SOLUTION.md ne doit PAS apparaître)
  ```

---

## Phase 5: Commit et Push
- [ ] Ajouter les changements:
  ```bash
  git add -A
  git status  # Vérifier avant
  ```

- [ ] Commiter:
  ```bash
  git commit -m "🔒 Sécuriser fichiers professeur avec chiffrement AES-256"
  ```

- [ ] Vérifier le commit n'inclut pas SOLUTION.md:
  ```bash
  git show HEAD --stat
  # Doit montrer:
  #   - SOLUTION.md.enc (new)
  #   - manage_teacher_files.sh (new)
  #   - .gitignore (modified)
  # (SOLUTION.md ne doit PAS apparaître)
  ```

- [ ] Push vers le serveur:
  ```bash
  git push origin main  # ou votre branche
  ```

---

## Phase 6: Vérification
- [ ] Aller sur GitHub / GitLab et vérifier:
  - ✅ `SOLUTION.md.enc` est visible
  - ❌ `SOLUTION.md` n'est PAS visible
  - ✅ `manage_teacher_files.sh` est visible

- [ ] Cloner le repo dans un dossier temporaire:
  ```bash
  mkdir /tmp/test-clone && cd /tmp/test-clone
  git clone <your-repo-url>
  cd <repo-name>
  ls -la
  # Vérifier:
  # ✅ SOLUTION.md.enc existe
  # ❌ SOLUTION.md n'existe pas
  rm -rf /tmp/test-clone
  ```

---

## Phase 7: Documentation et distribution

### Documentation (dans le repo)
- [ ] `TEACHER_FILE_SECURITY.md` est complet et clair
- [ ] `TEACHER_QUICKSTART.md` est lisible
- [ ] `SECURITY_ARCHITECTURE.md` explique le système

### Mot de passe
- [ ] Générer et stocker de manière sécurisée:
  
  **Options sécurisées:**
  - ✅ Gestionnaire de mots de passe (LastPass, 1Password, Bitwarden)
  - ✅ Créer un PDF chiffré avec le mot de passe
  - ✅ Email chiffré (PGP/GPG)
  - ✅ Service de partage sécurisé (Tresorit, Sync.com)
  - ✅ Plateforme pédagogique (Moodle, Canvas) en message privé

  **Options NON sécurisées:**
  - ❌ Slack public
  - ❌ Email clair
  - ❌ Mettons-le dans le README
  - ❌ Partager sur Discord sans chiffrement

- [ ] Partager le mot de passe AUX PROFS UNIQUEMENT
  Message modèle:
  ```
  Bonjour,
  
  Le dépôt Git du projet est maintenant sécurisé.
  
  Pour accéder aux fichiers de correction (SOLUTION.md), 
  utilisez la commande:
  
    ./manage_teacher_files.sh decrypt "<MOT_DE_PASSE>"
  
  Le mot de passe est: [INSÉRER MOT DE PASSE]
  
  Consultez TEACHER_QUICKSTART.md pour les détails.
  
  À bientôt,
  ```

---

## Phase 8: Test final (par un collègue prof)

**Demander à un autre prof de tester:**

- [ ] Clone le repo:
  ```bash
  git clone <repo>
  ```

- [ ] Essaie de lire SOLUTION.md:
  ```bash
  cat SOLUTION.md
  # Résultat: file not found ✅
  ```

- [ ] Essaie de lire SOLUTION.md.enc:
  ```bash
  cat SOLUTION.md.enc
  # Résultat: données aléatoires chiffrées (non lisible) ✅
  ```

- [ ] Reçoit le mot de passe
  
- [ ] Déchiffre:
  ```bash
  ./manage_teacher_files.sh decrypt "PASSED_PASSWORD"
  # Résultat: success ✅
  ```

- [ ] Lit la solution:
  ```bash
  cat SOLUTION.md
  # Résultat: content is visible ✅
  ```

- [ ] Confirme: tout fonctionne! ✅

---

## Phase 9: Communication aux étudiants

**Annoncer clairement:**

```
Bonjour,

Le projet inclut maintenant des fichiers de correction pour les profs,
sécurisés par chiffrement AES-256.

En tant qu'étudiant, vous ne verrez que:
- ✅ Les énoncés des exercices
- ✅ Les tests à faire passer
- ✅ Les guides pédagogiques
- ✅ Le script de gestion (pour info)

Vous ne verrez PAS:
- ❌ Les fichiers de correction (chiffrés)
- ❌ Le mot de passe

C'est normal et prévu! Cela fait partie du système pédagogique.

Bon travail,
```

---

## Phase 10: Maintenance

### Changer le mot de passe
1. Déchiffrer avec ancien pwd: `./manage_teacher_files.sh decrypt "OLD"`
2. Re-chiffrer avec nouveau pwd: `./manage_teacher_files.sh encrypt "NEW"`
3. Delete original: `rm SOLUTION.md`
4. Commit: `git add SOLUTION.md.enc && git commit -m "Update password"`
5. Partager nouveau pwd aux profs

### Ajouter/Modifier les solutions
1. Déchiffrer: `./manage_teacher_files.sh decrypt "PASSWORD"`
2. Éditer: `nano SOLUTION.md`
3. Re-chiffrer: `./manage_teacher_files.sh encrypt "PASSWORD"`
4. Delete original: `rm SOLUTION.md`
5. Commit: `git add SOLUTION.md.enc && git commit -m "Update solutions"`

### Audit
1. Vérifier que SOLUTION.md n'est jamais en Git:
   ```bash
   git log --all -- SOLUTION.md
   # Doit retourner: nothing
   ```

2. Vérifier que seul .enc est commité:
   ```bash
   git log --all -- SOLUTION.md.enc
   # Doit montrer: commits
   ```

---

## ✅ Résumé final

Après avoir suivi cette checklist:

✅ Les réponses sont chiffrées et en Git
✅ Les réponses originales ne sont jamais en Git
✅ Les étudiants ne peuvent pas les voir
✅ Les profs peuvent les déchiffrer facilement
✅ Le système est maintenable

**Bravo! Le système est déployé correctement! 🔒**

---

## 💬 Questions communes

**Q: Peut-on chiffrer aussi les autres fichiers (SECURITY_EXERCISE.md)?**
A: Non. Ce sont des fichiers publics volontaires. Laisser les ouverts.

**Q: Dois-je changer le mot de passe régulièrement?**
A: Non nécessaire. Changer si vous suspect une fuite. Sinon, une fois suffisant.

**Q: Et si un étudiant trouve le mot de passe?**
A: C'est une faille humaine. Peu de solutions tech. Mais bon: chiffrement montre l'intention, dissuade les curiosités.

**Q: Puis-je utiliser un fichier de clé à la place d'un mot de passe?**
A: Oui! Utiliser GPG: `gpg --encrypt --recipient "prof@university.edu" SOLUTION.md`. Plus sécurisé.

**Q: Et si j'oublie le mot de passe?**
A: Trop tard. Malheureusement, pas de récupération avec AES-256 symétrique. Gardez le password!
