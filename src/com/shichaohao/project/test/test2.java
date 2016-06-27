package com.shichaohao.project.test;

import java.io.IOException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;

import javax.swing.plaf.synth.SynthStyle;

import com.shichaohao.project.dao.DB;
import com.shichaohao.project.data.JudgeEmotion;

enum emotion{
	消极,中立,积极,
}
public class test2 {

	public static void main(String[] args) {
		System.out.println(emotion.values()[0]);	
	}
}
