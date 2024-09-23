package model.board.likey;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import model.board.BoardDto;

@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class LikeyDto {
	private String username;
	private long board_id;
}
