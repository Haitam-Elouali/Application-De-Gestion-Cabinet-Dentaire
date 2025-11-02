package ma.TeethCare;


import ma.TeethCare.conf.ApplicationContext;
import ma.TeethCare.mvc.controllers.modules.patient.api.PatientController;

public class MainApp
{
    public static void main( String[] args )
    {
        ApplicationContext.getBean(PatientController.class).showRecentPatients();
    }
}
