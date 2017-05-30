import com.spx.entity.*;
import com.spx.service.security.UserDetailsImpl;
import org.springframework.security.authentication.TestingAuthenticationToken;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;

import java.security.Principal;
import java.util.ArrayList;
import java.util.Date;


public class TestUtils {
    public static Principal CreateEmptyPrincipal(){
        return new Principal() {
            @Override
            public String getName() {
                return null;
            }
        };
    }

    public static Principal CreateNormalPrincipal(){
        ArrayList<GrantedAuthority> auths = new ArrayList<>();
        auths.add(new SimpleGrantedAuthority(UserEntity.Roles.ROLE_ORGANIZER.toString()));
        auths.add(new SimpleGrantedAuthority(UserEntity.Roles.ROLE_PARTICIPANT.toString()));
        auths.add(new SimpleGrantedAuthority(UserEntity.Roles.ROLE_SPECTATOR.toString()));
        UserDetailsImpl userImpl = new UserDetailsImpl(CreateUser(234L, "admin"), auths);
        return new TestingAuthenticationToken(userImpl, auths);
    }

    public static TournamentEntity CreateTournament(){
        TournamentEntity tournament = new TournamentEntity();
        tournament.setId(123L);
        tournament.setName("Tournament");
        tournament.setStartDate(new Date(2017, 8, 15));
        tournament.setEndDate(new Date(2017, 9, 15));
        return tournament;
    }

    public static UserEntity CreateUser(Long id, String name){
        UserEntity user = new UserEntity();
        user.setId(id);
        user.setLogin(name);
        user.setName(name);
        user.setRole(UserEntity.Roles.ROLE_ORGANIZER.toString());
        user.setExternal(false);
        user.setPassword("1");
        user.setActivated(true);
        return user;
    }

    public static TournamentInterestedUserEntity CreateInterestedUser(Long id, String name){
        UserEntity user = CreateUser(id, name);

        TournamentInterestedUserEntity intUser = new TournamentInterestedUserEntity();
        intUser.setUser(user);

        return intUser;
    }

    public static ArenaEntity CreateArena(Long id, String name){
        ArenaEntity arena = new ArenaEntity();

        arena.setId(id);
        arena.setMaxSpectators(16L);
        arena.setName(name);

        return arena;
    }
}
