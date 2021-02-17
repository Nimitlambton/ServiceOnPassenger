package com.lambtonserviceon;


public class User2 {


        String longi = "";
        String Lati = "";
        String Name = "";


        public User2(String longi, String lati, String name) {
            this.longi = longi;
            Lati = lati;
            Name = name;
        }

          public  String getLongi() {
            return longi;
        }

        public String getLati() {
            return Lati;
        }

        public String getName() {
            return Name;
        }

        public void setLongi(String longi) {
            this.longi = longi;
        }

        public void setLati(String lati) {
            Lati = lati;
        }

        public void setName(String name) {
            Name = name;
        }






}

