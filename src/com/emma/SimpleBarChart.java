package com.emma;

import java.awt.*;
import javax.swing.*;
import java.awt.event.*;
import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Set;

public class SimpleBarChart extends JPanel {
    private Integer[] value;
    private String[] letters;
    private String title;

    public SimpleBarChart(Integer[] val, String[] lett, String t) {
        letters = lett;
        value = val;
        title = t;
    }
    public void paintComponent(Graphics graphics) {
        super.paintComponent(graphics);
        if (value == null || value.length == 0)
            return;
        double minValue = 0;
        double maxValue = 0;
        for (int i = 0; i < value.length; i++) {
            if (minValue > value[i])
                minValue = value[i];
            if (maxValue < value[i])
                maxValue = value[i];
        }
        Dimension dim = getSize();
        int clientWidth = dim.width;
        int clientHeight = dim.height;
        int barWidth = clientWidth / value.length;
        Font titleFont = new Font("Book Antiqua", Font.BOLD, 15);
        FontMetrics titleFontMetrics = graphics.getFontMetrics(titleFont);
        Font labelFont = new Font("Book Antiqua", Font.PLAIN, 10);
        FontMetrics labelFontMetrics = graphics.getFontMetrics(labelFont);
        int titleWidth = titleFontMetrics.stringWidth(title);
        int q = titleFontMetrics.getAscent();
        int p = (clientWidth - titleWidth) / 2;
        graphics.setFont(titleFont);
        graphics.drawString(title, p, q);
        int top = titleFontMetrics.getHeight();
        int bottom = labelFontMetrics.getHeight();
        if (maxValue == minValue)
            return;
        double scale = (clientHeight - top - bottom) / (maxValue - minValue);
        q = clientHeight - labelFontMetrics.getDescent();
        graphics.setFont(labelFont);
        for (int j = 0; j < value.length; j++) {
            int valueP = j * barWidth + 1;
            int valueQ = top;
            int height = (int) (value[j] * scale);
            if (value[j] >= 0)
                valueQ += (int) ((maxValue - value[j]) * scale);
            else {
                valueQ += (int) (maxValue * scale);
                height = -height;
            }
            graphics.setColor(Color.blue);
            graphics.fillRect(valueP, valueQ, barWidth - 2, height);
            graphics.setColor(Color.black);
            graphics.drawRect(valueP, valueQ, barWidth - 2, height);
            int labelWidth = labelFontMetrics.stringWidth(letters[j]);
            p = j * barWidth + (barWidth - labelWidth) / 2;
            graphics.drawString(letters[j], p, q);

        }
    }
    public static void main(String[] args) throws IOException {
        HashMap<String, Integer> letters = new HashMap<String, Integer>();
        BufferedReader reader = new BufferedReader(new java.io.FileReader("C:\\Users\\eugen\\Desktop\\test.txt"));
        while (true){
            String line = reader.readLine();
            if (line==null){
                break;
            }
            for (int i = 0; i< line.length(); i++){
                char cc = line.charAt(i);
                if (Character.isLetter(cc)) {
                    String c = String.valueOf(line.charAt(i));
                    if (!c.isEmpty()) {
                        if (letters.containsKey(c)) {
                            letters.put(c, letters.get(c) + 1);
                        } else {
                            letters.put(c, 1);
                        }

                    }
                }
            }


        }
        //Close object
        reader.close();


        JFrame frame = new JFrame();
        frame.setSize(350, 300);

        // get the keys of the HashMap returned as an Array
        String[] key = letters.keySet().toArray(new String[0]);

        System.out.println(Arrays.toString(key));

        // get the values of the HashMap returned as an Array
        Integer[] values = letters.values().toArray(new Integer[0]);

        System.out.println(Arrays.toString(values));


        frame.getContentPane().add(new SimpleBarChart(values, key,
                "Letters"));

        WindowListener winListener = new WindowAdapter() {
            public void windowClosing(WindowEvent event) {
                System.exit(0);
            }
        };
        frame.addWindowListener(winListener);
        frame.setVisible(true);
    }
}