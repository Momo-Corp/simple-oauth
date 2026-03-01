# 🚀 CHIFFREMENT MAINTENANT - Guide rapide

## Option 1: Script automatique (recommandé)

```bash
# Rendre exécutable
chmod +x encrypt_now.sh

# Exécuter
./encrypt_now.sh
```

Le script va:
- ✅ Générer un mot de passe fort automatiquement
- ✅ Chiffrer SOLUTION.md → SOLUTION.md.enc
- ✅ Chiffrer QUICK_REFERENCE.md → QUICK_REFERENCE.md.enc
- ✅ Te donner les commandes pour la suite

---

## Option 2: Manuel (étape par étape)

### Étape 1: Générer un mot de passe
```bash
openssl rand -base64 24
```
📝 **Résultat:** `abc123XYZ...` → **NOTE-LE QUELQUE PART DE SÛR!**

### Étape 2: Chiffrer SOLUTION.md
```bash
./manage_teacher_files.sh encrypt "TON_MOT_DE_PASSE_ICI"
```

### Étape 3: Chiffrer QUICK_REFERENCE.md (optionnel)
```bash
# Modifier temporairement manage_teacher_files.sh pour QUICK_REFERENCE.md
# Ou utiliser openssl directement:
openssl enc -aes-256-cbc -salt -in QUICK_REFERENCE.md -out QUICK_REFERENCE.md.enc -k "TON_MOT_DE_PASSE"
```

### Étape 4: Vérifier
```bash
ls -la *.enc
# Doit afficher:
# SOLUTION.md.enc
# QUICK_REFERENCE.md.enc (si chiffré)
```

### Étape 5: Supprimer les originaux
```bash
rm SOLUTION.md QUICK_REFERENCE.md
```

### Étape 6: Vérifier Git
```bash
git status
# SOLUTION.md et QUICK_REFERENCE.md ne doivent PAS apparaître
# Seuls les .enc doivent apparaître
```

### Étape 7: Commiter
```bash
git add -A
git commit -m "🔒 Sécuriser fichiers prof avec AES-256"
git push
```

---

## Option 3: Commandes ultra-rapides (copier-coller)

```bash
# Générer mot de passe
PASSWORD=$(openssl rand -base64 24)
echo "Mot de passe: $PASSWORD"
echo "$PASSWORD" > .teacher_password.txt  # Sauvegarder temporairement

# Chiffrer
./manage_teacher_files.sh encrypt "$PASSWORD"
openssl enc -aes-256-cbc -salt -in QUICK_REFERENCE.md -out QUICK_REFERENCE.md.enc -k "$PASSWORD"

# Nettoyer
rm SOLUTION.md QUICK_REFERENCE.md

# Git
git status
git add SOLUTION.md.enc QUICK_REFERENCE.md.enc manage_teacher_files.sh .gitignore
git add encrypt_now.sh ENCRYPT_NOW.md
git commit -m "🔒 Sécuriser fichiers prof avec AES-256"

# Afficher le mot de passe une dernière fois
echo "N'oublie pas le mot de passe: $(cat .teacher_password.txt)"
# Puis supprimer le fichier temporaire:
rm .teacher_password.txt
```

---

## ⚠️  IMPORTANT

1. **Sauvegarde le mot de passe** avant de supprimer `.teacher_password.txt`
2. **Vérifie** que `git status` ne montre PAS `SOLUTION.md` (mais bien `SOLUTION.md.enc`)
3. **Partage** le mot de passe aux profs par canal sécurisé (email chiffré, Moodle, etc.)

---

## 🧪 Pour tester le déchiffrement

```bash
# Après avoir commité et poussé, tester:
./manage_teacher_files.sh decrypt "TON_MOT_DE_PASSE"
cat SOLUTION.md  # Doit afficher le contenu
rm SOLUTION.md   # Resécuriser
```

---

## ✅ Checklist finale

- [ ] Mot de passe généré et sauvegardé
- [ ] `SOLUTION.md` chiffré → `SOLUTION.md.enc`
- [ ] `QUICK_REFERENCE.md` chiffré → `QUICK_REFERENCE.md.enc`
- [ ] Originaux supprimés (`rm SOLUTION.md QUICK_REFERENCE.md`)
- [ ] `git status` ne montre pas les originaux
- [ ] Commité et poussé
- [ ] Mot de passe partagé aux profs (canal sécurisé)
- [ ] Testé le déchiffrement

---

## 🆘 En cas de problème

**"openssl: command not found"**
→ Installer OpenSSL: `apt-get install openssl` (Linux) ou `brew install openssl` (Mac)

**"Mot de passe perdu"**
→ Trop tard avec AES-256. Conserver soigneusement!

**"Git montre SOLUTION.md"**
→ Vérifier `.gitignore` contient `SOLUTION.md`
→ Exécuter `git rm --cached SOLUTION.md` puis re-commit

---

**Prêt? Lance:** `./encrypt_now.sh` ou suis Option 2/3 !
