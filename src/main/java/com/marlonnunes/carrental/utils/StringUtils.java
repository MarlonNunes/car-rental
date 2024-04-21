package com.marlonnunes.carrental.utils;

import java.security.SecureRandom;
import java.util.Objects;

public class StringUtils {


    private static final String CHAR_LOWER = "abcdefghijklmnopqrstuvwxyz";
    private static final String CHAR_UPPER = CHAR_LOWER.toUpperCase();
    private static final String NUMBER = "0123456789";
    private static final String DATA_FOR_RANDOM_STRING = CHAR_LOWER + CHAR_UPPER + NUMBER;
    private static final SecureRandom random = new SecureRandom();


    public static String verificationCodeGenerator() {
        int length = 16;

        StringBuilder sb = new StringBuilder(length);
        for (int i = 0; i < length; i++) {
            int rndCharAt = random.nextInt(DATA_FOR_RANDOM_STRING.length());
            char rndChar = DATA_FOR_RANDOM_STRING.charAt(rndCharAt);
            sb.append(rndChar);
        }

        return sb.toString();
    }

    public static String normalizeCpfOrCnpj(String document){
        if(Objects.isNull(document)) return null;
        return document.replaceAll("\\D", "").trim();
    }

    public static String formatCpfOrCnpj(String document) {
        if(Objects.isNull(document)) return null;

        document = document.replaceAll("\\D", "");

        if (document.length() == 11) {
            return formatCpf(document);
        } else if (document.length() == 14) {
            return formatCnpj(document);
        } else {
            return document;
        }
    }

    private static String formatCpf(String cpf) {
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    private static String formatCnpj(String cnpj) {
        return cnpj.replaceAll("(\\d{2})(\\d{3})(\\d{3})(\\d{4})(\\d{2})", "$1.$2.$3/$4-$5");
    }


    public static String formatCep(String cep) {
        if(Objects.isNull(cep)) return null;

        cep = cep.replaceAll("\\D", "");

        if (cep.length() == 8) {
            return cep.replaceAll("(\\d{5})(\\d{3})", "$1-$2");
        } else {
            return cep;
        }
    }

}
