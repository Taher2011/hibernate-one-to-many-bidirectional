package com.techgen.entity;

import java.util.Date;
import java.util.HashSet;
import java.util.Set;

import jakarta.persistence.CascadeType;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "trainer")
public class Trainer {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "trainer_name")
	private String name;

	@Column(name = "trainer_code")
	private String code;

	@Column(name = "joined_date")
	private Date joinedDate;

	@OneToMany(mappedBy = "trainer", cascade = CascadeType.PERSIST)
	private Set<Member> members = new HashSet<>();

	public Trainer(String name, String code, Date joinedDate) {
		super();
		this.name = name;
		this.code = code;
		this.joinedDate = joinedDate;
	}

	public Trainer addMember(Member member) {
		members.add(member);
		member.setTrainer(this);
		return this;
	}

	public void removeMember(Member member) {
		member.setTrainer(null);
	}

}
