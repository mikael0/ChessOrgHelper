import com.spx.dao.TournamentGameDao;
import com.spx.entity.TournamentGameEntity;
import com.spx.entity.UserEntity;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Александр on 29.05.2017.
 */
public class TestTournamentGameDaoImpl implements TournamentGameDao {
    private List<TournamentGameEntity> _games = new ArrayList<>();

    @Override
    public List<TournamentGameEntity> getAll() {
        return null;
    }

    @Override
    public List<TournamentGameEntity> getGamesByPlayer(UserEntity player) {
        return null;
    }

    @Override
    public List<TournamentGameEntity> getGamesByDate(Date date) {
        return null;
    }

    @Override
    public TournamentGameEntity getGameById(Long id) {
        return null;
    }

    @Override
    public Long addGame(TournamentGameEntity game) {
        _games.add(game);
        return game.getId();
    }

    @Override
    public void updateResult(TournamentGameEntity game) {

    }
}
