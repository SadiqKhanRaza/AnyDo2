package sadiq.raza.anydo;

public class GetDataAdapter {
        protected String notes;
        protected String time;
        protected int year;
        protected int month;
        protected int date;

        public GetDataAdapter(){}

        public GetDataAdapter(String notes, String time,int year,int month,int date) {
            this.notes = notes;
            this.time = time;
            this.year=year;
            this.date=date;
            this.month=month;
        }
}

