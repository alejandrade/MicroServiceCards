package com.gigamog.utils;

import java.util.ArrayList;

import com.gigamog.cardmodels.Card;


//all shufles alforithms should implement this
//sweet design patter magic for less code manipulation
public interface ShuffleAlgorithm {
	public void run(ArrayList<Card> cards);
}
