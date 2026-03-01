# ⚡ TL;DR - Sécurité fichiers prof

## 30 secondes pour sécuriser

```bash
# 1. Chiffrer
./manage_teacher_files.sh encrypt "VotreMotDePasse123!"

# 2. Nettoyer
rm SOLUTION.md QUICK_REFERENCE.md

# 3. Commiter
git add -A
git commit -m "🔒 sécuriser fichiers prof"
git push
```

---

## 30 secondes pour y accéder (prof)

```bash
./manage_teacher_files.sh decrypt "VotreMotDePasse123!"
cat SOLUTION.md
```

---

## Qui voit quoi

| Personne | Voit quoi |
|----------|-----------|
| **Étudiant** | ❌ Rien (fichier chiffré .enc) |
| **Prof** | ✅ Tout (après déchiffrement avec mot de passe) |
| **GitHub** | 🔐 Juste le fichier chiffré (inutile sans clé) |

---

## Le mot de passe

**Partager PAR:**
- Email chiffré ✅
- Moodle/Canvas ✅
- Conversation sécurisée ✅

**NE PAS partager par:**
- Git ❌
- Slack public ❌
- Email non chiffré ❌

---

## C'est tout!

- `SOLUTION.md.enc` → commiter
- `SOLUTION.md` → **JAMAIS** commiter
- `manage_teacher_files.sh` → à jour automatiquement

Voilà. Sécurisé. Simple.
