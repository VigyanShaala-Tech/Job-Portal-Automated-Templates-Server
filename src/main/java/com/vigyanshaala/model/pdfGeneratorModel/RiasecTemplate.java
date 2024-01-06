package com.vigyanshaala.model.pdfGeneratorModel;

public class RiasecTemplate {

        private  Long realistic;
        private  Long investigative;
        private  Long artistic;
        private  Long social;
        private  Long enterprising;
        private  Long conventional;
        private  String hollandCode;
        private  String studentEmail;

        public RiasecTemplate(){}

        RiasecTemplate(Long realistic, Long investigative, Long artistic, Long social, Long enterprising, Long conventional, String hollandCode, String studentEmail){


        this.realistic = realistic;
        this.investigative = investigative;
        this.artistic = artistic;
        this.social = social;
        this.enterprising = enterprising;
        this.conventional = conventional;
        this.hollandCode = hollandCode;
        this.studentEmail = studentEmail;

        }

        public Long getRealistic() {return realistic;}

        public void setRealistic(Long realistic) {
            this.realistic = realistic;
        }


        public Long getInvestigative() {return investigative;}

        public void setInvestigative(Long investigative) {
            this.investigative = investigative;
        }

        public Long getArtistic() {return artistic;}

        public void setArtistic(Long artistic) {
            this.artistic = artistic;
        }


        public Long getSocial() {return social;}

        public void setSocial(Long social) {
            this.social = social;
        }

        public Long getEnterprising() {return enterprising;}

        public void setEnterprising(Long enterprising) {
            this.enterprising = enterprising;
        }


        public Long getConventional() {return conventional;}

        public void setConventional(Long conventional) {
            this.conventional = conventional;
        }


        public String getHollandCode() {return hollandCode;}

        public void setHollandCode(String hollandCode) {
            this.hollandCode = hollandCode;
        }


        public String getStudentEmail() {
            return studentEmail;
        }

        public void setStudentEmail(String studentEmail) {
            this.studentEmail = studentEmail;
        }


}
