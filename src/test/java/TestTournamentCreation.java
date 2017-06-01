import com.spx.dao.TournamentDao;
import com.spx.entity.TournamentEntity;
import com.spx.service.security.UserDetailsImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;

public class TestTournamentCreation
{
    private TournamentEntity _tournament;
    private TournamentDao tournamentDao;

    public ResponseEntity<String> createTournament(Principal principal, @RequestBody TournamentEntity tournament) {
        if ((tournament == null) || (tournament.getName() == null) || (tournament.getStartDate() == null
        )           || (tournament.getEndDate()) == null) {
            return new ResponseEntity<String>(HttpStatus.BAD_REQUEST);
        }

        tournament.setChiefOrganizer(((UserDetailsImpl)((Authentication) principal).getPrincipal()).getUser());

        final Long tournamentId = tournamentDao.addTournament(tournament);

        return new ResponseEntity<String>(HttpStatus.OK);
    }

    @Before
    public void SetUp()
    {
        _tournament = new TournamentEntity();

        tournamentDao = new TestTournamentDaoImpl();
    }

    @Test
    public void testTournamentNull()
    {
        ResponseEntity<String> result = createTournament(TestUtils.CreateEmptyPrincipal(), null);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testTournamentNoDates()
    {
        _tournament.setName("NoDatesTournament");

        ResponseEntity<String> result = createTournament(TestUtils.CreateEmptyPrincipal(), _tournament);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected = ClassCastException.class)
    public void testTournamentBadPrincipal()
    {
        //Throws ClassCastException when trying to cast Principal to UserDetailImpl
        ResponseEntity<String> result = createTournament(TestUtils.CreateEmptyPrincipal(), TestUtils.CreateTournament());

        Assert.assertEquals(result, null);
    }

    @Test
    public void testTournamentGood()
    {
        ResponseEntity<String> result = createTournament(TestUtils.CreateNormalPrincipal(), TestUtils.CreateTournament());

        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }
}