package com.bolo.fit.utils;

import java.text.Normalizer;

public class StringUtils {

  public static int compararStringIgnorandoAcentuacao(String stringLeft, String stringRight) {
    String stringLeftSemAcentuacao = Normalizer.normalize(stringLeft, Normalizer.Form.NFD);
    String stringRightSemAcentuacao = Normalizer.normalize(stringRight, Normalizer.Form.NFD);
    return stringLeftSemAcentuacao.compareTo(stringRightSemAcentuacao);
  }
}
