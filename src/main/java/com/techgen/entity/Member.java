package com.techgen.entity;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import com.techgen.enums.MemberStatus;
import com.techgen.model.Address;

import jakarta.persistence.AttributeOverride;
import jakarta.persistence.AttributeOverrides;
import jakarta.persistence.CascadeType;
import jakarta.persistence.CollectionTable;
import jakarta.persistence.Column;
import jakarta.persistence.ElementCollection;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@NoArgsConstructor
@Entity
@Table(name = "member")
public class Member {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private long id;

	@Column(name = "member_name")
	private String name;

	@Column(name = "member_age")
	private int age;

	@Column(name = "membership_active")
	private boolean isActive;

	@Enumerated(EnumType.STRING)
	@Column(name = "member_status")
	private MemberStatus memberStatus;

	@ElementCollection
	@CollectionTable(name = "member_address", joinColumns = @JoinColumn(name = "member_id"))
	@AttributeOverrides({ @AttributeOverride(name = "zipcode", column = @Column(name = "zip_code")) })
	private Set<Address> address = new HashSet<>();

	@ElementCollection
	@CollectionTable(name = "member_mobile", joinColumns = @JoinColumn(name = "member_id"))
	@Column(name = "mobile_number")
	private List<String> mobileNumbers = new ArrayList<>();

	@ManyToOne(cascade = { CascadeType.PERSIST })
	@JoinColumn(name = "trainer_id")
	private Trainer trainer;

	public Member(String name, int age, boolean isActive, MemberStatus memberStatus, Trainer trainer) {
		super();
		this.name = name;
		this.age = age;
		this.isActive = isActive;
		this.memberStatus = memberStatus;
		this.trainer = trainer;
	}

	public Member(String name, int age, boolean isActive, MemberStatus memberStatus, List<String> mobileNumbers,
			Trainer trainer) {
		super();
		this.name = name;
		this.age = age;
		this.isActive = isActive;
		this.memberStatus = memberStatus;
		this.mobileNumbers = mobileNumbers;
		this.trainer = trainer;
	}

}
