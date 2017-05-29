import com.spx.dao.TournamentDao;
import com.spx.entity.*;

import java.util.List;

/**
 * Created by Александр on 29.05.2017.
 */
public class TestTournamentDaoImpl implements TournamentDao {
    @Override
    public List<TournamentEntity> getAll() {
        return null;
    }

    @Override
    public List<TournamentEntity> getTournamentsByOrganizer(Long orgId) {
        return null;
    }

    @Override
    public TournamentEntity getTournamentById(Long id) {
        return null;
    }

    @Override
    public RoomEntity getRoomById(Long id) {
        return null;
    }

    @Override
    public Long addTournament(TournamentEntity tournament) {
        return tournament.getId();
    }

    @Override
    public List<UserEntity> getInterestedUsers(TournamentEntity entity) {
        return null;
    }

    @Override
    public Long addHousing(HousingEntity housing) {
        return null;
    }

    @Override
    public Long addArena(ArenaEntity arena) {
        return null;
    }

    @Override
    public void updateTournament(TournamentEntity tournament) {

    }

    @Override
    public void updateRoom(RoomEntity room) {

    }
}
