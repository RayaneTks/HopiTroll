package Class;

import javax.swing.*;
import javax.swing.Timer;
import javax.swing.table.*;

import Interfaces.Regenerable;

import java.awt.*;
import java.awt.event.*;
import java.util.*;
import java.util.List;

public class MainGUI extends JFrame {
    private int jour = 1;
    private int actionsParJour = 5;
    private int actionsRestantes = actionsParJour;
    private int patientsCibles = 20; // Nombre total de patients à maintenir
    private int patientsGueris = 0;
    private int patientsMorts = 0;
    private int patientsRegeneres = 0;
    private int dureeJourneeSec = 180; // 3 minutes par journée
    private int tempsRestantSec = dureeJourneeSec;

    private JLabel labelJour;
    private JLabel labelActions;
    private JLabel labelStats;
    private JLabel labelTimer;
    private JLabel labelNbPatients;
    private JProgressBar progressBar;
    private JTextArea logArea;
    private JButton btnSoigner;
    private JButton btnPasserJournee;

    private JComboBox<String> comboMedecin;
    private JTable tablePatients;
    private DefaultTableModel patientsModel;

    private List<ServiceMedical> services;
    private List<Medecin> medecins;
    private List<Creature> tousLesPatients;

    private Timer timerJournee;

    public MainGUI(List<ServiceMedical> services, List<Medecin> medecins) {
        super("HopiTroll - Simulation Hôpital Fantastique");
        this.services = services;
        this.medecins = medecins;
        this.tousLesPatients = new ArrayList<>();
        for (ServiceMedical s : services) tousLesPatients.addAll(s.getCreatures());

        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setSize(1200, 750);
        setLayout(new BorderLayout());

        // Haut : infos journée, actions, stats, timer, nb patients
        JPanel panelInfos = new JPanel(new GridLayout(2, 3));
        labelJour = new JLabel("Jour : " + jour);
        labelActions = new JLabel("Actions restantes : " + actionsRestantes);
        labelStats = new JLabel(statsTexte());
        labelTimer = new JLabel("Temps restant : " + formatTime(tempsRestantSec));
        labelNbPatients = new JLabel("Patients dans l'hôpital : " + getNbPatients());
        progressBar = new JProgressBar(0, dureeJourneeSec);
        progressBar.setValue(tempsRestantSec);
        panelInfos.add(labelJour);
        panelInfos.add(labelActions);
        panelInfos.add(labelStats);
        panelInfos.add(labelTimer);
        panelInfos.add(labelNbPatients);
        panelInfos.add(progressBar);
        add(panelInfos, BorderLayout.NORTH);

        // Centre : table des patients
        patientsModel = new DefaultTableModel(
                new Object[]{"Nom", "Service", "État", "Moral", "Maladies", "Jours d'attente", "Bonus/Malus"}, 0);
        tablePatients = new JTable(patientsModel);
        tablePatients.setRowHeight(28);
        tablePatients.setDefaultRenderer(Object.class, new PatientCellRenderer());
        JScrollPane scrollPatients = new JScrollPane(tablePatients);
        add(scrollPatients, BorderLayout.CENTER);

        // Bas : actions et log
        JPanel panelBas = new JPanel(new BorderLayout());

        JPanel panelActions = new JPanel(new FlowLayout());
        comboMedecin = new JComboBox<>();
        for (Medecin m : medecins) comboMedecin.addItem(m.getNom());
        btnSoigner = new JButton("Soigner le patient sélectionné");
        btnPasserJournee = new JButton("Passer à la journée suivante");
        panelActions.add(new JLabel("Médecin :"));
        panelActions.add(comboMedecin);
        panelActions.add(btnSoigner);
        panelActions.add(btnPasserJournee);

        panelBas.add(panelActions, BorderLayout.NORTH);

        logArea = new JTextArea(6, 60);
        logArea.setEditable(false);
        logArea.setBackground(new Color(230, 230, 255));
        JScrollPane scrollLog = new JScrollPane(logArea);
        panelBas.add(scrollLog, BorderLayout.CENTER);

        add(panelBas, BorderLayout.SOUTH);

        // Listeners
        btnSoigner.addActionListener(e -> soignerAction());
        btnPasserJournee.addActionListener(e -> passerJourneeAction());

        // Affichage initial
        log("Bienvenue dans HopiTroll !");
        rafraichirAffichage();

        // Timer de la journée (décrémente le temps restant chaque seconde)
        timerJournee = new Timer(1000, e -> tickJournee());
        timerJournee.start();
    }

    class PatientCellRenderer extends DefaultTableCellRenderer {
        @Override
        public Component getTableCellRendererComponent(
                JTable table, Object value, boolean isSelected, boolean hasFocus, int row, int column) {
            Component c = super.getTableCellRendererComponent(table, value, isSelected, hasFocus, row, column);
            String etat = (String) table.getValueAt(row, 2);
            if ("Guéri".equals(etat)) c.setBackground(new Color(180, 255, 180));
            else if ("Critique".equals(etat)) c.setBackground(new Color(255, 180, 180));
            else if ("Mort".equals(etat)) c.setBackground(Color.LIGHT_GRAY);
            else if ("Régénéré".equals(etat)) c.setBackground(new Color(180, 255, 255));
            else c.setBackground(new Color(255, 255, 200));
            if (isSelected) c.setBackground(new Color(120, 180, 255));
            return c;
        }
    }

    private void soignerAction() {
        if (actionsRestantes <= 0) {
            log("Plus d'actions pour aujourd'hui !");
            return;
        }
        int idxMed = comboMedecin.getSelectedIndex();
        int idxRow = tablePatients.getSelectedRow();
        if (idxMed < 0 || idxRow < 0) {
            log("Sélectionnez un médecin et un patient !");
            return;
        }
        Medecin med = medecins.get(idxMed);
        Creature patient = tousLesPatients.get(idxRow);

        String etat = getEtatPatient(patient);
        if ("Mort".equals(etat)) {
            log("Impossible de soigner un patient mort !");
            return;
        }
        if ("Guéri".equals(etat)) {
            log("Ce patient est déjà guéri !");
            return;
        }

        patient.setSoigneCeJour(true);
        log(med.getNom() + " soigne " + patient.getNomComplet() + " (effet à la fin de la journée)");
        actionsRestantes--;
        labelActions.setText("Actions restantes : " + actionsRestantes);
        rafraichirAffichage();
    }

    private void passerJourneeAction() {
        evolutionAutomatique();
        jour++;
        actionsRestantes = actionsParJour + jour / 5;
        tempsRestantSec = dureeJourneeSec - jour * 10;
        if (tempsRestantSec < 60) tempsRestantSec = 60;
        progressBar.setMaximum(tempsRestantSec);
        progressBar.setValue(tempsRestantSec);

        // Compléter jusqu'à patientsCibles patients dans l'hôpital
        int totalPatients = getNbPatients();
        int aAjouter = patientsCibles - totalPatients;
        for (int i = 0; i < aAjouter; i++) {
            // Choisir un service au hasard
            ServiceMedical svc = services.get(new Random().nextInt(services.size()));
            Creature nouveau = creerPatientAleatoire();
            svc.ajouterCreature(nouveau);
            log("Nouveau patient dans " + svc.getNom() + " : " + nouveau.getNomComplet());
        }

        // S'assurer qu'il y a au moins 1 patient par service
        for (ServiceMedical svc : services) {
            if (svc.getCreatures().isEmpty()) {
                Creature nouveau = creerPatientAleatoire();
                svc.ajouterCreature(nouveau);
                log("Ajout d'urgence : " + nouveau.getNomComplet() + " dans " + svc.getNom());
            }
        }

        log("---- Début de la journée " + jour + " ----");
        rafraichirAffichage();
    }

    private void evolutionAutomatique() {
        List<Creature> aSupprimer = new ArrayList<>();
        Random rand = new Random();
        for (ServiceMedical svc : services) {
            for (Creature c : new ArrayList<>(svc.getCreatures())) {
                // Gestion de l'attente
                c.incrementerJoursAttente();

                // Gestion des maladies multiples
                List<Maladie> maladies = new ArrayList<>(c.getMaladies());
                for (Maladie m : maladies) {
                    // Soin différé
                    if (c.isSoigneCeJour()) {
                        double tirage = rand.nextDouble();
                        if (tirage < 0.6) {
                            m.diminuerNiveau();
                            log("Soin efficace pour " + c.getNomComplet() + " !");
                        } else if (tirage < 0.9) {
                            log("Soin sans effet pour " + c.getNomComplet() + ".");
                        } else {
                            m.augmenterNiveau();
                            logMalus(c, "Soin inefficace, l'état empire !", Color.ORANGE);
                        }
                        c.setSoigneCeJour(false);
                    } else {
                        // Maladie empire avec faible chance de s'aggraver fortement si patient attend trop
                        if (c.getJoursAttente() > 2 && rand.nextDouble() < 0.2) {
                            m.augmenterNiveau();
                            logMalus(c, "L'attente aggrave la maladie de " + c.getNomComplet() + " !", Color.ORANGE);
                        } else {
                            m.augmenterNiveau();
                        }
                    }

                    // Passage en critique si maladie assez grave
                    if (m.getNiveauActuel() >= m.getNiveauMax() - 1 && !c.isCritique()) {
                        c.setCritique(true);
                        logMalus(c, c.getNomComplet() + " passe en état critique !", Color.RED);
                    }

                    // Mort rare si critique
                    if (c.isCritique() && rand.nextDouble() < 0.05) {
                        boolean mort = c.trepasser(tousLesPatients);
                        if (mort) {
                            logMalus(c, "Mort du patient critique !", Color.RED);
                            patientsMorts++;
                            aSupprimer.add(c);
                        }
                    }

                    // Guérison
                    if (m.getNiveauActuel() == 0) {
                        c.getMaladies().remove(m);
                        logBonus(c, "Guérison !", new Color(0, 180, 0));
                        patientsGueris++;
                        if (c.getMaladies().isEmpty()) {
                            aSupprimer.add(c);
                        }
                    }
                }

                // Chance d'attraper une nouvelle maladie si déjà malade
                if (!c.getMaladies().isEmpty() && rand.nextDouble() < 0.1) {
                    Maladie nouvelle = new Maladie("Surinfection", "INF", 1, 2 + rand.nextInt(2));
                    c.tomberMalade(nouvelle);
                    logMalus(c, c.getNomComplet() + " a attrapé une nouvelle maladie !", Color.MAGENTA);
                }
            }
        }
        for (Creature c : aSupprimer) {
            for (ServiceMedical svc : services) svc.enleverCreature(c);
        }
        rafraichirAffichage();
    }

    private void tickJournee() {
        tempsRestantSec--;
        labelTimer.setText("Temps restant : " + formatTime(tempsRestantSec));
        progressBar.setValue(tempsRestantSec);
        if (tempsRestantSec <= 0) {
            log("Temps écoulé ! Passage automatique à la journée suivante.");
            passerJourneeAction();
        }
    }

    private void rafraichirAffichage() {
        tousLesPatients = new ArrayList<>();
        patientsModel.setRowCount(0);
        for (ServiceMedical svc : services) {
            for (Creature c : svc.getCreatures()) {
                tousLesPatients.add(c);
                String etat = getEtatPatient(c);
                String maladies = c.getMaladies().isEmpty() ? "Aucune" :
                        String.join(", ", c.getMaladies().stream().map(Maladie::getNomComplet).toArray(String[]::new));
                int joursAttente = c.getJoursAttente();
                String bonusMalus = "";
                if ("Régénéré".equals(etat)) bonusMalus = "💀+Contamination";
                else if ("Guéri".equals(etat)) bonusMalus = "🎉";
                else if ("Mort".equals(etat)) bonusMalus = "☠️";
                patientsModel.addRow(new Object[]{
                        c.getNomComplet(), svc.getNom(), etat, c.getMoral(), maladies, joursAttente, bonusMalus
                });
            }
        }
        labelJour.setText("Jour : " + jour);
        labelActions.setText("Actions restantes : " + actionsRestantes);
        labelStats.setText(statsTexte());
        labelTimer.setText("Temps restant : " + formatTime(tempsRestantSec));
        labelNbPatients.setText("Patients dans l'hôpital : " + getNbPatients());
        progressBar.setValue(tempsRestantSec);
    }

    private String getEtatPatient(Creature c) {
        if (!c.getMaladies().isEmpty()) {
            if (c.isCritique()) return "Critique";
            return "Malade";
        }
        return "Guéri";
    }

    private int getNbPatients() {
        int total = 0;
        for (ServiceMedical svc : services) total += svc.getCreatures().size();
        return total;
    }

    private void log(String msg) {
        logArea.append(msg + "\n");
        logArea.setCaretPosition(logArea.getDocument().getLength());
    }

    private void logBonus(Creature c, String msg, Color color) {
        logArea.setForeground(color);
        log("🎉 " + c.getNomComplet() + " : " + msg);
        logArea.setForeground(Color.BLACK);
        JOptionPane.showMessageDialog(this, msg, "Bonus", JOptionPane.INFORMATION_MESSAGE);
    }

    private void logMalus(Creature c, String msg, Color color) {
        logArea.setForeground(color);
        log("☠️ " + c.getNomComplet() + " : " + msg);
        logArea.setForeground(Color.BLACK);
        JOptionPane.showMessageDialog(this, msg, "Malus", JOptionPane.WARNING_MESSAGE);
    }

    private String statsTexte() {
        return "<html>Guéris : <span style='color:green'>" + patientsGueris + "</span> | "
                + "Morts : <span style='color:red'>" + patientsMorts + "</span> | "
                + "Régénérés : <span style='color:blue'>" + patientsRegeneres + "</span></html>";
    }

    private String formatTime(int sec) {
        int min = sec / 60;
        int s = sec % 60;
        return String.format("%02d:%02d", min, s);
    }

    private Creature creerPatientAleatoire() {
        String[] noms = {"Alrik", "Brunhilda", "Drogon", "Saphira", "Tyrion", "Ygritte", "Thorin", "Lurtz"};
        String nom = noms[new Random().nextInt(noms.length)] + " " + (int) (Math.random() * 100);
        int moral = 50 + new Random().nextInt(50);
        Creature c;
        switch (new Random().nextInt(4)) {
            case 0: c = new Elfe(nom, "M", 70, 1.8, 100, moral); break;
            case 1: c = new Orque(nom, "M", 100, 2.0, 40, moral); break;
            case 2: c = new Zombie(nom, "M", 80, 1.7, 30, moral); break;
            default: c = new Nain(nom, "M", 90, 1.4, 120, moral); break;
        }
        c.tomberMalade(new Maladie("Maladie mystère", "MYS", 1 + new Random().nextInt(3), 2 + new Random().nextInt(2)));
        return c;
    }

    public static void main(String[] args) {
        List<ServiceMedical> services = new ArrayList<>();
        List<Medecin> medecins = new ArrayList<>();

        ServiceStandard serviceElfes = new ServiceStandard("Salle des Elfes", 50, 5, Budget.FAIBLE, TypeCreature.ELFE);
        ServiceStandard serviceOrques = new ServiceStandard("Salle des Orques", 50, 5, Budget.FAIBLE, TypeCreature.ORQUE);

        Elfe elfe1 = new Elfe("Legolas", "M", 70, 1.85, 120, 80);
        Orque orque1 = new Orque("Ugluk", "M", 120, 2.00, 35, 70);
        elfe1.tomberMalade(new Maladie("Grippe", "GRP", 1, 2));
        orque1.tomberMalade(new Maladie("Peste", "PST", 2, 2));

        serviceElfes.ajouterCreature(elfe1);
        serviceOrques.ajouterCreature(orque1);

        services.add(serviceElfes);
        services.add(serviceOrques);

        Medecin med1 = new Medecin("House", "M", 45, TypeCreature.ELFE);
        Medecin med2 = new Medecin("Bones", "F", 40, TypeCreature.ORQUE);

        medecins.add(med1);
        medecins.add(med2);

        SwingUtilities.invokeLater(() -> new MainGUI(services, medecins).setVisible(true));
    }
}