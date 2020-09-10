package sim.ERPID.repository;

import sim.ERPID.domain.Member;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.simple.SimpleJdbcInsert;
import javax.sql.DataSource;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

public class JdbcTemplateMemberRepository implements MemberRepository {
    private final JdbcTemplate jdbcTemplate;
    public JdbcTemplateMemberRepository(DataSource dataSource) {
        jdbcTemplate = new JdbcTemplate(dataSource);
    }
    @Override
    public Member save(Member member) {
        SimpleJdbcInsert jdbcInsert = new SimpleJdbcInsert(jdbcTemplate);
        jdbcInsert.withTableName("member").usingGeneratedKeyColumns("id");

        Map<String, Object> parameters = new HashMap<>();
        parameters.put("name", member.getName());

        Number key = jdbcInsert.executeAndReturnKey(new
                MapSqlParameterSource(parameters));
        member.setId(key.longValue());
        return member;
    }
    @Override
    public Optional<Member> findById(Long id) {
        List<Member> result = jdbcTemplate.query("select * from member where id = ?", memberRowMapper(), id);
        return result.stream().findAny();
    }

    @Override
    public List<Member> findAll() {
        return jdbcTemplate.query("select * from member", memberRowMapper());
    }

    @Override
    public Optional<Member> findByName(String name) {
        List<Member> result = jdbcTemplate.query("select * from member where name = ?", memberRowMapper(), name);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByNumber(Integer number) {
        List<Member> result = jdbcTemplate.query("select * from member where number = ?", memberRowMapper(), number);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByPw(String pw) {
        List<Member> result = jdbcTemplate.query("select * from member where pw = ?", memberRowMapper(), pw);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByEmail(String email) {
        List<Member> result = jdbcTemplate.query("select * from member where email = ?", memberRowMapper(), email);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findBySex(String sex) {
        List<Member> result = jdbcTemplate.query("select * from member where sex = ?", memberRowMapper(), sex);
        return result.stream().findAny();
    }

    @Override
    public Optional<Member> findByAddress(String address) {
        List<Member> result = jdbcTemplate.query("select * from member where address = ?", memberRowMapper(), address);
        return result.stream().findAny();
    }

    private RowMapper<Member> memberRowMapper() {
        return (rs, rowNum) -> {
            Member member = new Member();
            member.setId(rs.getLong("id"));
            member.setName(rs.getString("name"));
            member.setPw(rs.getString("pw"));
            member.setNumber(rs.getInt("number"));
            member.setEmail(rs.getString("email"));
            member.setSex(rs.getString("sex"));
            member.setAddress(rs.getString("address"));


            return member;
        };
    }
}
