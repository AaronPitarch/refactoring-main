package com.kreitek.refactor.Refactorizado.controllers;

import com.kreitek.refactor.Refactorizado.Controller;
import com.kreitek.refactor.Refactorizado.modelos.DNI;
import com.kreitek.refactor.Refactorizado.TIPODNI;

public class NIEController implements Controller {

    @Override
    public void pintar() {
        // creamos un NIE correcto
        DNI nieCorrecto = new DNI(TIPODNI.NIE, "X0932707B");
        boolean esValidoNie = (nieCorrecto.validarDNI() == 1);
        System.out.println( "NIE " + nieCorrecto.numDNI + " es: " + (esValidoNie));

        // creamos un NIE incorrecto
        DNI nieIncorrecto = new DNI(TIPODNI.NIE, "Z2691139Z");
        boolean esValidoNieIncorrecto = (nieIncorrecto.validarDNI() == 1);
        System.out.println( "NIE " + nieIncorrecto.numDNI + " es: " + (esValidoNieIncorrecto));
    }
}
