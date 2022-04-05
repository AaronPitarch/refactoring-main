package com.kreitek.refactor.Refactorizado.controllers;

import com.kreitek.refactor.Refactorizado.Controller;
import com.kreitek.refactor.Refactorizado.modelos.DNI;
import com.kreitek.refactor.Refactorizado.TIPODNI;

public class CIFController implements Controller {

    @Override
    public void pintar() {
        // creamos un CIF correcto
        DNI cifCorrecto = new DNI(TIPODNI.CIF, "W9696294I");
        boolean esValidoCIF = (cifCorrecto.validarDNI() == 1);
        System.out.println( "CIF " + cifCorrecto.numDNI + " es: " + esValidoCIF);

        // creamos un CIF incorrecto
        DNI cifIncorrecto = new DNI(TIPODNI.CIF, "W9696294A");
        boolean esValidoCifIncorrecto = (cifIncorrecto.validarDNI() == 1);
        System.out.println( "NIE " + cifIncorrecto.numDNI + " es: " + esValidoCifIncorrecto);
    }
}
