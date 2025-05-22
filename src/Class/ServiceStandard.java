package Class;
public class ServiceStandard extends ServiceMedical {
    public ServiceStandard(String nom, double superficie, int capaciteMax, Budget budget, TypeCreature typeCreature) {
        super(nom, superficie, capaciteMax, budget, typeCreature);
    }

    @Override
    public void reviserBudget() {
        // Exemple simple : augmente le budget si le service est plein
        if (creatures.size() == capaciteMax) {
            budget = Budget.FAIBLE;
        }
    }
}
