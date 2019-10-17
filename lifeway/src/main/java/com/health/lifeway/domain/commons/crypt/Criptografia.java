package com.health.lifeway.domain.commons.crypt;

public class Criptografia {

    public String criptografar(String senha) {

        String alfabeto = "<abcdefghijklmnopqrstuvwxyzçéáíúóãõABCDEFGHIJKLMNOPQRSTUVWXYZÇÁÉÓÍÚÃÕ1234567890.;:?,º]}§[{ª!@#$%&*()_+-=\\/|\'\">";

        char[] t = senha.toCharArray();
        String palavra = "";
        for (int i = 0; i < t.length; i++) {

            int posicao = alfabeto.indexOf(t[i]) + 5;

            if (alfabeto.length() <= posicao) {
                posicao = posicao - alfabeto.length();
            }

            palavra = palavra + alfabeto.charAt(posicao);

        }

        return palavra;
    }

}
