package ru.ea.dao;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.jdbc.AutoConfigureTestDatabase;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.boot.test.autoconfigure.orm.jpa.TestEntityManager;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ExtendWith(SpringExtension.class)
@DataJpaTest
@AutoConfigureTestDatabase(replace = AutoConfigureTestDatabase.Replace.NONE)
public class GenreDaoTest {
    @Autowired
    private TestEntityManager entityManager;
    @Autowired
    private GenreRepository dao;
    @Autowired
    private UserRepository userDao;




    @Test
    @DisplayName("подсчитывать кол-во юзеров. Текущий метод: count")
    public void whenUserCount_thenCorrect() {

        assertEquals(3, userDao.count());
    }

    @AfterEach
    public void c() {

    }

   /*  @Test
    @DisplayName("выдавать список всех жанров. Текущий метод: findAll")
    public void whenGetAll_thenCorrect() {
             List<Genre> list = dao.findAll();

        assertArrayEquals(new String[]{"Художественная литература","Публицистика"},
                list.stream().map(e->e.getName()).toArray());
    }

    @Test
    @DisplayName("Находить запись жанра по его названию. Текущий метод: findOptionalByName")
    public void whenGetByName_thenCorrect() {

        Optional<Genre> genreOptional = dao.findOptionalByName("Художественная литература");

        assertNotNull(genreOptional.get().getId());
    }

    @Test
    @DisplayName("Находить жанра по id. Текущий метод: findById")
    public void givenGenreAded_whenGetById_thenOk() {
        Genre genre=new Genre("программирование");
       entityManager.persist(genre);
        UUID id=genre.getId();

        Optional<Genre> optionalGenre= dao.findById(id);

        assertTrue(optionalGenre.isPresent());
        assertEquals(genre, optionalGenre.get());
    }

    @Test
    @DisplayName("Возвращать пустой Optional, если жанра с такми названием не существует. Текущий метод: findOptionalByName")
    public void givenNoRecordWithName_whenGetByName_thenException() {

        Optional<Genre> optionalGenre=dao.findOptionalByName("несуществующий жанр");
        assertFalse(optionalGenre.isPresent());
    }

    @Test
    @DisplayName("Добавлять новый жанр в таблицу жанров и возвращать UUID. Текущий метод: insert")
    public void whenInsert_thenAdded() {

        Genre genre= dao.save(new Genre("Научная литература"));

        assertNotNull(genre);
        assertEquals("Научная литература", genre.getName());
    }


    @Test
    @DisplayName("Удалять жанр по названию. Текущий метод: deleteByName")
    public void whenDelete_thenDeleted() {


       int deletedCount= dao.deleteByName("Художественная литература");

        assertEquals(1, deletedCount);

        Optional<Genre> optionalGenre=dao.findOptionalByName("Художественная литература");
        assertFalse(optionalGenre.isPresent());

    }
*/


}
