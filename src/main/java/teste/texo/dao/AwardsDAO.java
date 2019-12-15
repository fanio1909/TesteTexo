package teste.texo.dao;

import com.google.gson.Gson;
import teste.texo.dto.AwardsDTO;
import teste.texo.dto.ProducerDTO;

import java.io.InputStream;
import java.util.*;

public class AwardsDAO {
    private final List<AwardsDTO> awards = new ArrayList<>();

    public AwardsDAO() {
        initializeDatabase();
    }

    private void initializeDatabase() {
        InputStream inputStream = getClass().getClassLoader().getResourceAsStream("awards.csv");
        Scanner s = new Scanner(inputStream).useDelimiter("\\A");
        String result = s.hasNext() ? s.next() : "";

        String[] resultList = result.split("\\r?\\n");
        String[] columns = resultList[0].split(";");
        Gson gson = new Gson();

        for (int i = 1; i < resultList.length; i++) {
            String[] row = resultList[i].split(";");
            Map<String, String> rowMap = new HashMap<>();

            for (int j = 0; j < row.length; j++) {
                rowMap.put(columns[j], row[j]);
            }

            awards.add(gson.fromJson(gson.toJsonTree(rowMap), AwardsDTO.class));
        }
    }

    private Map<String, List<AwardsDTO>> getWinnersList() {
        Map<String, List<AwardsDTO>> winnersList = new HashMap<>();

        for (AwardsDTO award : awards) {
            if ((award.getWinner() != null) && (award.getWinner().equals("yes"))) {
                for (String producer : award.getProducersAsList()) {
                    if (!winnersList.containsKey(producer)) {
                        winnersList.put(producer, new ArrayList<>());
                    }

                    winnersList.get(producer).add(award);
                }
            }
        }

        for (List<AwardsDTO> winnerAwards : winnersList.values()) {
            winnerAwards.sort((o1, o2) -> o1.getYear().compareTo(o2.getYear()));
        }

        return winnersList;
    }

    public ProducerDTO getShortestIntervalAward() {
        Map<String, ProducerDTO> winnersIntervals = new HashMap<>();

        Map<String, List<AwardsDTO>> winnersList = getWinnersList();
        for (Map.Entry<String, List<AwardsDTO>> winner : winnersList.entrySet()) {
            List<AwardsDTO> winnerAwards = winner.getValue();

            if (winnerAwards.size() < 2) continue;

            ProducerDTO producerDTO = new ProducerDTO();
            producerDTO.setProducer(winner.getKey());
            producerDTO.setInterval(winnerAwards.get(1).getYear() - winnerAwards.get(0).getYear());
            producerDTO.setPreviousWin(winnerAwards.get(0).getYear());
            producerDTO.setFollowingWin(winnerAwards.get(1).getYear());

            AwardsDTO lastAward = winnerAwards.get(0);
            for (AwardsDTO comparingAward : winnerAwards) {
                int comparingInterval = comparingAward.getYear() - lastAward.getYear();

                if ((comparingInterval > 0) && (comparingInterval < producerDTO.getInterval())) {
                    producerDTO.setInterval(comparingInterval);
                    producerDTO.setPreviousWin(lastAward.getYear());
                    producerDTO.setFollowingWin(comparingAward.getYear());
                }

                lastAward = comparingAward;
            }

            winnersIntervals.put(winner.getKey(), producerDTO);
        }

        return winnersIntervals.entrySet()
                .stream()
                .sorted((o1, o2) -> (o1.getValue().getInterval() - o2.getValue().getInterval()))
                .findFirst()
                .get()
                .getValue();
    }

    public ProducerDTO getLongestIntervalAward() {
        Map<String, ProducerDTO> winnersIntervals = new HashMap<>();

        Map<String, List<AwardsDTO>> winnersList = getWinnersList();
        for (Map.Entry<String, List<AwardsDTO>> winner : winnersList.entrySet()) {
            List<AwardsDTO> winnerAwards = winner.getValue();

            if (winnerAwards.size() < 2) continue;

            ProducerDTO producerDTO = new ProducerDTO();
            producerDTO.setProducer(winner.getKey());
            producerDTO.setInterval(winnerAwards.get(1).getYear() - winnerAwards.get(0).getYear());
            producerDTO.setPreviousWin(winnerAwards.get(0).getYear());
            producerDTO.setFollowingWin(winnerAwards.get(1).getYear());

            AwardsDTO lastAward = winnerAwards.get(0);
            for (AwardsDTO comparingAward : winnerAwards) {
                int comparingInterval = comparingAward.getYear() - lastAward.getYear();

                if (comparingInterval > producerDTO.getInterval()) {
                    producerDTO.setInterval(comparingInterval);
                    producerDTO.setPreviousWin(lastAward.getYear());
                    producerDTO.setFollowingWin(comparingAward.getYear());
                }

                lastAward = comparingAward;
            }

            winnersIntervals.put(winner.getKey(), producerDTO);
        }

        return winnersIntervals.entrySet()
                .stream()
                .sorted((o1, o2) -> (o2.getValue().getInterval() - o1.getValue().getInterval()))
                .findFirst()
                .get()
                .getValue();
    }
}
