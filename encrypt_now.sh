#!/bin/bash
# 🔐 CHIFFREMENT RAPIDE - À exécuter maintenant!

echo "🔐 CHIFFREMENT DES FICHIERS PROF"
echo "================================"
echo ""

# 1. Générer un mot de passe fort
echo "1️⃣  Génération d'un mot de passe sécurisé..."
PASSWORD=$(openssl rand -base64 24)
echo "   Mot de passe généré: $PASSWORD"
echo ""
echo "⚠️  IMPORTANT: Enregistre ce mot de passe quelque part de SÛR!"
echo "   (gestionnaire de mots de passe, fichier sécurisé, etc.)"
echo ""
read -p "Appuie sur ENTRÉE quand tu as noté le mot de passe..." 

# 2. Vérifier les fichiers
echo ""
echo "2️⃣  Vérification des fichiers..."
if [ -f "SOLUTION.md" ]; then
    echo "   ✅ SOLUTION.md trouvé"
else
    echo "   ❌ SOLUTION.md non trouvé!"
    exit 1
fi

if [ -f "QUICK_REFERENCE.md" ]; then
    echo "   ✅ QUICK_REFERENCE.md trouvé"
else
    echo "   ⚠️  QUICK_REFERENCE.md non trouvé (optionnel)"
fi
echo ""

# 3. Chiffrer SOLUTION.md
echo "3️⃣  Chiffrement de SOLUTION.md..."
openssl enc -aes-256-cbc -salt -in "SOLUTION.md" -out "SOLUTION.md.enc" -k "$PASSWORD"
if [ $? -eq 0 ]; then
    echo "   ✅ SOLUTION.md.enc créé"
else
    echo "   ❌ Erreur lors du chiffrement"
    exit 1
fi

# 4. Chiffrer QUICK_REFERENCE.md (si existe)
if [ -f "QUICK_REFERENCE.md" ]; then
    echo "4️⃣  Chiffrement de QUICK_REFERENCE.md..."
    openssl enc -aes-256-cbc -salt -in "QUICK_REFERENCE.md" -out "QUICK_REFERENCE.md.enc" -k "$PASSWORD"
    if [ $? -eq 0 ]; then
        echo "   ✅ QUICK_REFERENCE.md.enc créé"
    fi
fi
echo ""

# 5. Vérifier les fichiers chiffrés
echo "5️⃣  Vérification..."
ls -lh *.enc
echo ""

# 6. Instructions finales
echo "6️⃣  PROCHAINES ÉTAPES:"
echo ""
echo "   a. Supprimer les originaux:"
echo "      rm SOLUTION.md QUICK_REFERENCE.md"
echo ""
echo "   b. Vérifier git status:"
echo "      git status"
echo ""
echo "   c. Ajouter les fichiers chiffrés:"
echo "      git add SOLUTION.md.enc QUICK_REFERENCE.md.enc manage_teacher_files.sh .gitignore"
echo ""
echo "   d. Commiter:"
echo "      git commit -m '🔒 Sécuriser fichiers prof avec AES-256'"
echo ""
echo "   e. Push:"
echo "      git push"
echo ""
echo "7️⃣  PARTAGER LE MOT DE PASSE:"
echo "   Mot de passe: $PASSWORD"
echo "   À partager aux profs par canal sécurisé (email chiffré, Moodle, etc.)"
echo ""
echo "✅ Terminé!"
