import com.spx.dao.TournamentDao;
import com.spx.entity.TournamentEntity;
import com.spx.entity.UserEntity;
import com.spx.service.security.UserDetailsImpl;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.web.bind.annotation.RequestBody;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;

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

    private Principal CreateEmptyPrincipal()
    {
        return new Principal() {
            @Override
            public String getName() {
                return null;
            }
        };
    }

    @Test
    public void testTournamentNull()
    {
        ResponseEntity<String> result = createTournament(CreateEmptyPrincipal(), null);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test
    public void testTournamentNoDates()
    {
        _tournament.setName("NoDatesTournament");

        ResponseEntity<String> result = createTournament(CreateEmptyPrincipal(), _tournament);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.BAD_REQUEST);
    }

    @Test(expected = ClassCastException.class)
    public void testTournamentBadPrincipal()
    {
        _tournament.setName("BadPrincipalTournament");
        _tournament.setStartDate(new Date(2017, 8, 15));
        _tournament.setEndDate(new Date(2017, 9, 1));

        //Throws ClassCastException when trying to cast Principal to UserDetailImpl
        ResponseEntity<String> result = createTournament(CreateEmptyPrincipal(), _tournament);

        Assert.assertEquals(result, null);
    }

    @Test
    public void testTournamentGood()
    {
        _tournament.setName("NormalTournament");
        _tournament.setStartDate(new Date(2017, 8, 15));
        _tournament.setEndDate(new Date(2017, 9, 1));

        UserEntity user = new UserEntity();
        user.setName("admin");
        user.setRole(UserEntity.Roles.ROLE_ORGANIZER.toString());
        user.setLogin("admin");
        user.setExternal(false);
        user.setPassword("1");
        user.setActivated(true);
        ArrayList<GrantedAuthority> auths = new ArrayList<>();
        auths.add(new SimpleGrantedAuthority(UserEntity.Roles.ROLE_ORGANIZER.toString()));
        auths.add(new SimpleGrantedAuthority(UserEntity.Roles.ROLE_PARTICIPANT.toString()));
        auths.add(new SimpleGrantedAuthority(UserEntity.Roles.ROLE_SPECTATOR.toString()));
        UserDetailsImpl userImpl = new UserDetailsImpl(user, auths);
        Principal principal = new TestingAuthenticationToken(userImpl, auths);

        ResponseEntity<String> result = createTournament(principal, _tournament);

        Assert.assertEquals(result.getStatusCode(), HttpStatus.OK);
    }
}