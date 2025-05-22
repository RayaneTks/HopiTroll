package Interfaces;
import java.util.List;
import Class.Creature;
import Class.Maladie;


public interface Contagieuse {
    void contaminer(Creature cible, Maladie maladie);
}

