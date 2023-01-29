package com.techgen.client;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import com.techgen.entity.Member;
import com.techgen.entity.Trainer;
import com.techgen.enums.MemberStatus;
import com.techgen.model.Address;
import com.techgen.util.HibernateUtil;

public class Client {

	public static void main(String[] args) {

		SessionFactory sessionFactory = null;

		Session session = null;

		try {
			sessionFactory = HibernateUtil.getSessionFactory();

			session = sessionFactory.getCurrentSession();

			Transaction transaction = session.getTransaction();

			transaction.begin();

			// persistMemberTrainer(session);
			// persistTrainerMember(session);
			// persistMember(session);
			// persistTrainer(session);
			// updateMembersTrainer(session);
			// updateMember(session);
			// updateTrainerToMember(session);
			// updateTrainer(session);
			// deleteMember(session);
			// deleteTrainer(session);

			transaction.commit();

		} catch (HibernateException e) {

			System.out.println(e.getMessage());

		} finally {

			if (session != null) {
				session.close();
			}

			if (sessionFactory != null) {
				sessionFactory.close();
			}

		}
	}

	private static void updateMember(Session session) {
		Member member = getMember(session, 2l);
		member.setAddress(createAddress());
		member.setMobileNumbers(createMobileNumber());
	}

	private static void updateMembersTrainer(Session session) {
		Member member = getMember(session, 2l);
		Trainer trainer = getTrainer(session, 5l);
		member.setMemberStatus(MemberStatus.HALF_YEARLY);
		Set<Address> addresses = createAddress();
		member.setAddress(addresses);
		member.setTrainer(trainer);
	}

	private static void updateTrainerToMember(Session session) {
		Trainer trainer = getTrainer(session, 2l);
		Member member = getMember(session, 6l);
		member.setActive(false);
		member.setMemberStatus(MemberStatus.MONTHLY);
		member.setTrainer(trainer);
		trainer.addMember(member);
	}

	private static void updateTrainer(Session session) {
		Trainer trainer = getTrainer(session, 2l);
		trainer.setCode("NEW-CODE");
	}

	private static Trainer getTrainer(Session session, long id) {
		return session.get(Trainer.class, id);
	}

	private static Member getMember(Session session, long id) {
		return session.get(Member.class, id);
	}

	private static void persistMemberTrainer(Session session) {
		Trainer trainer1 = new Trainer("Raj", "Tra-7373", new Date());
		Trainer trainer2 = new Trainer("Rahul", "Dia-7373", new Date());
		Trainer trainer3 = new Trainer("Akshay", "Abs-7373", new Date());

		Member member1 = new Member("Taher", 30, true, MemberStatus.HALF_YEARLY, trainer2);
		Member member2 = new Member("Amit", 30, false, MemberStatus.MONTHLY, trainer1);
		Member member3 = new Member("Vikky", 30, true, MemberStatus.HALF_YEARLY, trainer2);
		Member member4 = new Member("Mohan", 30, false, MemberStatus.ANNUALLY, trainer3);
		Member member5 = new Member("Drish", 30, true, MemberStatus.QUATERLY, trainer1);

		List<Member> members = new ArrayList<>();
		members.add(member1);
		members.add(member2);
		members.add(member3);
		members.add(member4);
		members.add(member5);

		for (Member member : members) {
			session.persist(member);
		}
	}

	private static void persistTrainerMember(Session session) {
		Trainer trainer1 = new Trainer("Raj", "Tra-7373", new Date());
		Trainer trainer2 = new Trainer("Rahul", "Dia-7373", new Date());
		Trainer trainer3 = new Trainer("Akshay", "Abs-7373", new Date());

		Member member1 = new Member("Taher", 30, true, MemberStatus.HALF_YEARLY,
				Arrays.asList("8796545852", "8796545874"), trainer2);
		Member member2 = new Member("Amit", 30, false, MemberStatus.MONTHLY, trainer1);
		Member member3 = new Member("Vikky", 30, true, MemberStatus.HALF_YEARLY, trainer2);
		Member member4 = new Member("Mohan", 30, false, MemberStatus.ANNUALLY, trainer3);
		Member member5 = new Member("Drish", 30, true, MemberStatus.QUATERLY, trainer1);

		List<Trainer> trainers = new ArrayList<>();
		trainers.add(trainer1.addMember(member2));
		trainers.add(trainer1.addMember(member5));
		trainers.add(trainer2.addMember(member1));
		trainers.add(trainer2.addMember(member3));
		trainers.add(trainer3.addMember(member4));

		for (Trainer trainer : trainers) {
			session.persist(trainer);
		}
	}

	private static void persistMember(Session session) {
		Member member = new Member("Mohan", 30, true, MemberStatus.QUATERLY, null);
		member.setAddress(createAddress());
		member.setMobileNumbers(createMobileNumber());
		session.persist(member);
	}

	private static Set<Address> createAddress() {
		Set<Address> addresses = new HashSet<>();
		Address address1 = new Address();
		address1.setStreet("AB Road");
		address1.setCity("Indore");
		address1.setZipcode("452012");
		Address address2 = new Address();
		address2.setStreet("BN Road");
		address2.setCity("Bhopal");
		address2.setZipcode("878989");
		addresses.add(address1);
		addresses.add(address2);
		return addresses;
	}

	private static List<String> createMobileNumber() {
		List<String> mobileNumbers = new ArrayList<>();
		mobileNumbers.add("896535698");
		mobileNumbers.add("893335698");
		return mobileNumbers;
	}

	private static void persistTrainer(Session session) {
		Trainer trainer = new Trainer("Rahul", "Dia-7373", new Date());
		session.persist(trainer);
	}

	private static void deleteMember(Session session) {
		Member member = getMember(session, 1l);
		session.remove(member);
	}

	private static void deleteTrainer(Session session) {
		Trainer trainer = getTrainer(session, 2l);
		Set<Member> members = trainer.getMembers();
		for (Member member : members) {
			trainer.removeMember(member);
		}
		session.remove(trainer);
	}
}
