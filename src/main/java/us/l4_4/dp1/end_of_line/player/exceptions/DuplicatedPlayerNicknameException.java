/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package us.l4_4.dp1.end_of_line.player.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ResponseStatus;

/**
 *
 * @author japarejo
 */
@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class DuplicatedPlayerNicknameException extends RuntimeException{

	/**
	 * 
	 */
	private static final long serialVersionUID = -3330551940727004798L;
	
	public DuplicatedPlayerNicknameException() {
		super("You can't have two nicknames equals.");
	}
    
}
