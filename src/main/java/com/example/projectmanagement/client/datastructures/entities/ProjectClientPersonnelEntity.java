package com.example.projectmanagement.client.datastructures.entities;

import jakarta.persistence.Entity;
import jakarta.persistence.FetchType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Data;

@Entity
@Data
@Table(name = "project_client_personnels")
public class ProjectClientPersonnelEntity {

	@Id
	@GeneratedValue(strategy = GenerationType.IDENTITY)
	private Integer id;
	
	@ManyToOne(fetch = FetchType.LAZY, optional = true)
	@JoinColumn(name = "project_client_id", nullable = true)
	private ProjectClientEntity projectClient;
	
	private String position;
	private String note;
	private String email;
	private String phone;
	private String firstName;
	private String middleName;
	private String lastName;

	/*
	 * TODO: 後々実装！クライアントユーザーとしてプロジェクトに参加できるよう紐づける。
	 *メールアドレスを利用して招待メールを送れるようにする。
	 *TOKENか何かを用意して識別してクライアントユーザーとして正式にチーム参加できるようにする。
	 *構想：
	 *ファイルアップロードやスケジュールの閲覧、テスト運用の機能別の報告など
	 */
	private Integer userId;
}
