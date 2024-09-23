package model.board.comments;

import java.sql.Date;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
@Getter
@Setter
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class CommentsDto {
	private long id;
	private String content;
	private Date postDate;
	private long board_id;
	private String username;
	
}
