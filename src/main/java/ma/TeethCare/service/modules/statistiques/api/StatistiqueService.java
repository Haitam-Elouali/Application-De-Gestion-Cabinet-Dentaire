package ma.TeethCare.service.modules.statistiques.api;

import ma.TeethCare.service.modules.statistiques.dto.StatsView;

public interface StatistiqueService {

	StatsView calculerParPeriode(String dateDebutInclusive, String dateFinExclusive);
}


