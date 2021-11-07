package ba.unsa.etf.rs.tutorijal6.util;

import ba.unsa.etf.rs.tutorijal6.Racun;

@FunctionalInterface
public interface KreditnaSposobnost {
    double provjeri(Racun racun);
}
