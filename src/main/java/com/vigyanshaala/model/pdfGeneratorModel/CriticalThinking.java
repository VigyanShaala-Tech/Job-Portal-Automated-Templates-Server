package com.vigyanshaala.model.pdfGeneratorModel;

public class CriticalThinking {
        private  String answerA;
        private  String answerB;
        private  String answerC;
        private  String studentEmail;

        public CriticalThinking(){}
    CriticalThinking(String answerA, String answerB, String answerC, String studentEmail) {
            this.answerA = answerA;
            this.answerB = answerB;
            this.answerC = answerC;
            this.studentEmail = studentEmail;
        }
        public String getAnswerA() {return answerA;}

        public void setAnswerA(String answerA) {
            this.answerA = answerA;
        }

        public String getAnswerB() {return answerB;}

        public void setAnswerB(String answerB) {
            this.answerB = answerB;
        }

        public String getAnswerC() {return answerC;}

        public void setAnswerC(String answerC) {
            this.answerC = answerC;
        }

        public String getStudentEmail() {
            return studentEmail;
        }

        public void setStudentEmail(String studentEmail) {
            this.studentEmail = studentEmail;
        }

}
