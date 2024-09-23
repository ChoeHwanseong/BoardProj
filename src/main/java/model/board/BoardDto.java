package model.board;

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
public class BoardDto {
	private long id;
	private String username;
	private String title;
	private String content;
	private int hitcount;
	private int likey;
	private String files;
	private Date postDate;
	private long commentscount;
}
