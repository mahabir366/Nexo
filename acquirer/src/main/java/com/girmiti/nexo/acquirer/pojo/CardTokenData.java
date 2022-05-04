package com.girmiti.nexo.acquirer.pojo;

import java.io.Serializable;

import lombok.Getter;
import lombok.Setter;

@Setter
@Getter
public class CardTokenData extends CardToken implements Serializable {

	private static final long serialVersionUID = 1625819755421904722L;

	private String userId;

	private String password;
}
