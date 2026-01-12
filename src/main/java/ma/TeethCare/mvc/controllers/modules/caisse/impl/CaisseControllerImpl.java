package ma.TeethCare.mvc.controllers.modules.caisse.impl;

import ma.TeethCare.mvc.controllers.modules.caisse.api.CaisseController;
import ma.TeethCare.service.modules.auth.dto.UserPrincipal;
import ma.TeethCare.service.modules.caisse.api.chargesService;
import ma.TeethCare.service.modules.caisse.api.revenuesService;
import ma.TeethCare.service.modules.caisse.api.situationFinanciereService;
import ma.TeethCare.mvc.ui.pages.caisse.CaissePanel;
import javax.swing.JPanel;

public class CaisseControllerImpl implements CaisseController {

    private final chargesService chargesService;
    private final revenuesService revenuesService;
    private final situationFinanciereService situationFinanciereService;

    public CaisseControllerImpl(chargesService chargesService, revenuesService revenuesService, situationFinanciereService situationFinanciereService) {
        this.chargesService = chargesService;
        this.revenuesService = revenuesService;
        this.situationFinanciereService = situationFinanciereService;
    }

    @Override
    public JPanel getView(UserPrincipal principal) {
        return new CaissePanel(this, chargesService, revenuesService, situationFinanciereService, principal);
    }
}
