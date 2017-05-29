import com.spx.dao.TournamentDao;
import com.spx.dao.TournamentGameDao;
import com.spx.entity.TournamentEntity;
import com.spx.entity.TournamentGameEntity;
import com.spx.entity.TournamentInterestedUserEntity;
import com.spx.utils.GenerateSchedule;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.*;

public class TestScheduleGeneration {
    TournamentDao tournamentDao;
    TournamentGameDao tournamentGameDao;
    TournamentEntity _tournament;

    public ResponseEntity<String> generateSchedule(Principal principal,
                                                   @RequestBody Map<String, Long> data){

        Long tournamentId = data.get("tournamentId");

        TournamentEntity tournament = tournamentDao.getTournamentById(tournamentId);

        List<TournamentGameEntity> games = GenerateSchedule.generateSchedule(tournament);

        tournamentDao.updateTournament(tournament);

        for (TournamentGameEntity game : games) {
            tournamentGameDao.addGame(game);
        }

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @Before
    public void SetUp()
    {
        tournamentDao = new TestTournamentDaoImpl();
        tournamentGameDao = new TestTournamentGameDaoImpl();
        _tournament = TestUtils.CreateTournament();

        tournamentDao.addTournament(_tournament);

        //users
        Set<TournamentInterestedUserEntity> users = new HashSet<>();
        users.add(TestUtils.CreateInterestedUser(1L, "user1"));
        users.add(TestUtils.CreateInterestedUser(2L, "user2"));
        users.add(TestUtils.CreateInterestedUser(3L, "user3"));
        users.add(TestUtils.CreateInterestedUser(4L, "user4"));

        _tournament.setInterestedUsers(users);
        _tournament.setMaxParticipantsNum(4L);

        //arenas
        _tournament.addArena(TestUtils.CreateArena(1L, "arena1"));
    }

    @Test
    public void testGenerateNormalTournament()
    {
        Map<String, Long> data = new HashMap<>();

        data.put("tournamentId", _tournament.getId());

        ResponseEntity<String> result = generateSchedule(TestUtils.CreateNormalPrincipal(), data);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }


}
