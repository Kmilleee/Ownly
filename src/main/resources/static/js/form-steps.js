function nextStep() {
    const step1 = document.getElementById('infos');
    const step2 = document.getElementById('withdrawal');
    const btnNext = document.getElementById('btn-next');
    const btnSave = document.getElementById('btn-save');

    // Récupère les champs de l'étape 1 (infos)
    const fieldsStep1 = step1.querySelectorAll('input, select, textarea');

    // On boucle sur chaque champ de l'étape 1 pour voir s'il est bon
    for (let field of fieldsStep1) {
        if (!field.checkValidity()) {
            // Si un champ est invalide, on affiche affiche l'erreur
            field.reportValidity();
            return;
        }
    }

    // Gestion de l'affichage des inputs (si tout est ok)
    step1.classList.add('hidden');
    step2.classList.remove('hidden');
    // Gestion de l'affichage des boutons (si tout est ok)
    btnNext.classList.add('hidden');
    btnSave.classList.remove('hidden');
}