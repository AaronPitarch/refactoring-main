package com.kreitek.refactor.Refactorizado.controllers;

import com.kreitek.refactor.Refactorizado.Controller;
import com.kreitek.refactor.Refactorizado.modelos.DNI;
import com.kreitek.refactor.Refactorizado.TIPODNI;

public class DNIController implements Controller {

    @Override
    public void pintar() {
        // creamos un DNI correcto
        DNI dniCorrecto = new DNI(TIPODNI.DNI, "11111111H");
        boolean esValido = (dniCorrecto.validarDNI() == 1);
        System.out.println( "DNI " + dniCorrecto.numDNI + " es: " + (esValido));

        // creamos un DNI incorrecto
        DNI dniIncorrecto01 = new DNI(TIPODNI.DNI, "24324356A");
        boolean esValido01 = (dniIncorrecto01.validarDNI() == 1);
        System.out.println( "DNI " + dniIncorrecto01.numDNI + " es: " + (esValido01));
    }
}
