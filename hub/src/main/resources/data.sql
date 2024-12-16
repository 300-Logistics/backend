-- 허브 데이터 삽입
INSERT INTO p_hub (id, name, address, latitude, longitude, is_deleted)
VALUES (UNHEX(REPLACE('6f7e3d8d-6dcd-429d-9119-7de6d637a6f2', '-', '')), '서울특별시 센터', '서울특별시 송파구 송파대로 55', 37.4742027808565, 127.123621185562, false),
       (UNHEX(REPLACE('3e47c1fb-5b3f-44b8-9cbf-d5c7d93aee53', '-', '')), '경기 북부 센터', '경기도 고양시 덕양구 권율대로 570', 37.6403771056018, 126.87379545786, false),
       (UNHEX(REPLACE('6d8a768d-9a74-40cc-9d0c-26eec6e3c111', '-', '')), '경기 남부 센터', '경기도 이천시 덕평로 257-21', 37.1896213142136, 127.375050006958, false),
       (UNHEX(REPLACE('7f4d3776-e364-4cf3-9174-e56ffded57b5', '-', '')), '부산광역시 센터', '부산 동구 중앙대로 206', 35.117605126596, 129.045060216345, false),
       (UNHEX(REPLACE('9b5ec8c5-03c2-4238-8533-57fa0a230dfe', '-', '')), '대구광역시 센터', '대구 북구 태평로 161', 35.8758849492106, 128.596129208483, false),
       (UNHEX(REPLACE('bd9fa8e1-e3ad-4322-b5a4-9c5664bdb235', '-', '')), '인천광역시 센터', '인천 남동구 정각로 29', 37.4560499608337, 126.705255744089, false),
       (UNHEX(REPLACE('2189d8e0-df3b-4ab2-b7f3-cb327afbe7ca', '-', '')), '광주광역시 센터', '광주 서구 내방로 111', 35.1600994105234, 126.851461925213, false),
       (UNHEX(REPLACE('30a94d7f-bc9a-4bc5-9b04-d57ff52f5f28', '-', '')), '대전광역시 센터', '대전 서구 둔산로 100', 36.3503849976553, 127.384633005948, false),
       (UNHEX(REPLACE('64b2c03f-e5ff-4e76-9ff4-07b8ed9e5e56', '-', '')), '울산광역시 센터', '울산 남구 중앙로 201', 35.5379472830778, 129.311256608093, false),
       (UNHEX(REPLACE('eabf09b2-8c8e-4db0-bf84-93736b82e9c5', '-', '')), '세종특별자치시 센터', '세종특별자치시 한누리대로 2130', 36.4800579897497, 127.289039408864, false),
       (UNHEX(REPLACE('477b5475-7b68-4094-8998-4b947be7d6d4', '-', '')), '강원특별자치도 센터', '강원특별자치도 춘천시 중앙로 1', 37.8800729197963, 127.727907820318, false),
       (UNHEX(REPLACE('85272e78-2b7d-4219-8f49-7246072c2fbb', '-', '')), '충청북도 센터', '충북 청주시 상당구 상당로 82', 36.6353867908159, 127.491428436987, false),
       (UNHEX(REPLACE('0848e1eb-d249-460f-a9b2-c4e41376481d', '-', '')), '충청남도 센터', '충남 홍성군 홍북읍 충남대로 21', 36.6590666265439, 126.672978750559, false),
       (UNHEX(REPLACE('28dbf587-6c51-4c84-96b4-cf1d568ab66b', '-', '')), '전북특별자치도 센터', '전북특별자치도 전주시 완산구 효자로 225', 35.8194621650578, 127.106396942356, false),
       (UNHEX(REPLACE('48d4bff9-bd31-48f4-a6f6-02f9b65d89b7', '-', '')), '전라남도 센터', '전남 무안군 삼향읍 오룡길 1', 34.8174988528003, 126.465423854957, false),
       (UNHEX(REPLACE('0de5607f-bd1b-4325-907d-9535066e4410', '-', '')), '경상북도 센터', '경북 안동시 풍천면 도청대로 455', 36.5761205474728, 128.505722686385, false),
       (UNHEX(REPLACE('3a8ea73f-bc29-4c9b-9531-e5c79fe75c18', '-', '')), '경상남도 센터', '경남 창원시 의창구 중앙대로 300', 35.2378032514675, 128.691940442146, false);



-- 허브 연결 데이터 삽입
INSERT INTO p_hub_connection (id, start_hub_id, end_hub_id, distance, duration)
VALUES
-- 경기남부 - 경기북부
        (UNHEX(REPLACE('6d609277-398a-4997-bcf3-f4319552b981', '-', '')), UNHEX(REPLACE('6d8a768d-9a74-40cc-9d0c-26eec6e3c111', '-', '')), UNHEX(REPLACE('3e47c1fb-5b3f-44b8-9cbf-d5c7d93aee53', '-', '')), 100, 121),
        (UNHEX(REPLACE('f0ed0bbb-c5a6-40b3-a678-a9dc0e0ab199', '-', '')), UNHEX(REPLACE('3e47c1fb-5b3f-44b8-9cbf-d5c7d93aee53', '-', '')), UNHEX(REPLACE('6d8a768d-9a74-40cc-9d0c-26eec6e3c111', '-', '')), 99, 93),

-- 경기남부 - 서울
        (UNHEX(REPLACE('06da6164-8492-48a9-95c9-1c4ae3298d72', '-', '')), UNHEX(REPLACE('6d8a768d-9a74-40cc-9d0c-26eec6e3c111', '-', '')), UNHEX(REPLACE('6f7e3d8d-6dcd-429d-9119-7de6d637a6f2', '-', '')), 69, 57),
        (UNHEX(REPLACE('5931572e-185a-4c96-b1b1-ee102a013e1c', '-', '')), UNHEX(REPLACE('6f7e3d8d-6dcd-429d-9119-7de6d637a6f2', '-', '')), UNHEX(REPLACE('6d8a768d-9a74-40cc-9d0c-26eec6e3c111', '-', '')), 57, 63),

-- 경기남부 - 인천
        (UNHEX(REPLACE('f737c5f3-8f66-45af-94d8-0081f0f44ce6', '-', '')), UNHEX(REPLACE('6d8a768d-9a74-40cc-9d0c-26eec6e3c111', '-', '')), UNHEX(REPLACE('bd9fa8e1-e3ad-4322-b5a4-9c5664bdb235', '-', '')), 80, 125),
        (UNHEX(REPLACE('c1f2a0cb-387f-44d8-9b57-8439fc3a93a3', '-', '')), UNHEX(REPLACE('bd9fa8e1-e3ad-4322-b5a4-9c5664bdb235', '-', '')), UNHEX(REPLACE('6d8a768d-9a74-40cc-9d0c-26eec6e3c111', '-', '')), 81, 87),

-- 경기남부 - 강원도
        (UNHEX(REPLACE('ead938bd-e4b1-4910-a08b-84af01567fb3', '-', '')), UNHEX(REPLACE('6d8a768d-9a74-40cc-9d0c-26eec6e3c111', '-', '')), UNHEX(REPLACE('477b5475-7b68-4094-8998-4b947be7d6d4', '-', '')), 139, 105),
        (UNHEX(REPLACE('009fc788-23fc-43d0-aef9-4f56563a91e8', '-', '')), UNHEX(REPLACE('477b5475-7b68-4094-8998-4b947be7d6d4', '-', '')), UNHEX(REPLACE('6d8a768d-9a74-40cc-9d0c-26eec6e3c111', '-', '')), 139, 110),

-- 경기남부 - 대전
        (UNHEX(REPLACE('ca796511-2981-42a1-ba62-a795937d43e8', '-', '')), UNHEX(REPLACE('6d8a768d-9a74-40cc-9d0c-26eec6e3c111', '-', '')), UNHEX(REPLACE('30a94d7f-bc9a-4bc5-9b04-d57ff52f5f28', '-', '')), 113, 128),
        (UNHEX(REPLACE('ca363538-aef8-4bc6-b235-16e8bca7374b', '-', '')), UNHEX(REPLACE('30a94d7f-bc9a-4bc5-9b04-d57ff52f5f28', '-', '')), UNHEX(REPLACE('6d8a768d-9a74-40cc-9d0c-26eec6e3c111', '-', '')), 113, 116),

-- 경기남부 - 대구
        (UNHEX(REPLACE('27f1b018-d414-4c74-a3f0-3ab2a6be34c7', '-', '')), UNHEX(REPLACE('6d8a768d-9a74-40cc-9d0c-26eec6e3c111', '-', '')), UNHEX(REPLACE('9b5ec8c5-03c2-4238-8533-57fa0a230dfe', '-', '')), 242, 207),
        (UNHEX(REPLACE('9b8f4bf3-876d-4d5d-b13e-768f64540216', '-', '')), UNHEX(REPLACE('9b5ec8c5-03c2-4238-8533-57fa0a230dfe', '-', '')), UNHEX(REPLACE('6d8a768d-9a74-40cc-9d0c-26eec6e3c111', '-', '')), 231, 170),

-- 대전 - 충청남도
        (UNHEX(REPLACE('ef8642ff-959f-4942-b261-53a9475c7d24', '-', '')), UNHEX(REPLACE('30a94d7f-bc9a-4bc5-9b04-d57ff52f5f28', '-', '')), UNHEX(REPLACE('0848e1eb-d249-460f-a9b2-c4e41376481d', '-', '')), 87, 79),
        (UNHEX(REPLACE('147a5717-4ae7-42d7-95d9-1505df109e1d', '-', '')), UNHEX(REPLACE('0848e1eb-d249-460f-a9b2-c4e41376481d', '-', '')), UNHEX(REPLACE('30a94d7f-bc9a-4bc5-9b04-d57ff52f5f28', '-', '')), 90, 82),

-- 대전 - 충청북도
        (UNHEX(REPLACE('94cc8dba-0206-478d-b5f8-7ce19b3809ce', '-', '')), UNHEX(REPLACE('30a94d7f-bc9a-4bc5-9b04-d57ff52f5f28', '-', '')), UNHEX(REPLACE('85272e78-2b7d-4219-8f49-7246072c2fbb', '-', '')), 39, 63),
        (UNHEX(REPLACE('846ec57a-1cf6-4f23-b278-362f09560629', '-', '')), UNHEX(REPLACE('85272e78-2b7d-4219-8f49-7246072c2fbb', '-', '')), UNHEX(REPLACE('30a94d7f-bc9a-4bc5-9b04-d57ff52f5f28', '-', '')), 40, 70),

-- 대전 - 세종
        (UNHEX(REPLACE('979b18f6-9a9e-4667-af8d-39ad380993be', '-', '')), UNHEX(REPLACE('30a94d7f-bc9a-4bc5-9b04-d57ff52f5f28', '-', '')), UNHEX(REPLACE('eabf09b2-8c8e-4db0-bf84-93736b82e9c5', '-', '')), 27, 34),
        (UNHEX(REPLACE('6fa1fe9d-8c48-4b92-ad5c-02319899be4f', '-', '')), UNHEX(REPLACE('eabf09b2-8c8e-4db0-bf84-93736b82e9c5', '-', '')), UNHEX(REPLACE('30a94d7f-bc9a-4bc5-9b04-d57ff52f5f28', '-', '')), 26, 35),

-- 대전 - 전라북도
        (UNHEX(REPLACE('456aea7f-0908-408d-aa3c-8f658ebcb3c4', '-', '')), UNHEX(REPLACE('30a94d7f-bc9a-4bc5-9b04-d57ff52f5f28', '-', '')), UNHEX(REPLACE('28dbf587-6c51-4c84-96b4-cf1d568ab66b', '-', '')), 90, 89),
        (UNHEX(REPLACE('3fb0042c-2699-4d66-a7f9-e749ebb811f1', '-', '')), UNHEX(REPLACE('28dbf587-6c51-4c84-96b4-cf1d568ab66b', '-', '')), UNHEX(REPLACE('30a94d7f-bc9a-4bc5-9b04-d57ff52f5f28', '-', '')), 88, 90),

-- 대전 - 광주
        (UNHEX(REPLACE('847af829-a4cc-42dc-9311-06b2950f6d1a', '-', '')), UNHEX(REPLACE('30a94d7f-bc9a-4bc5-9b04-d57ff52f5f28', '-', '')), UNHEX(REPLACE('2189d8e0-df3b-4ab2-b7f3-cb327afbe7ca', '-', '')), 169, 138),
        (UNHEX(REPLACE('fdc38593-f900-412a-9a91-193e6a374479', '-', '')), UNHEX(REPLACE('2189d8e0-df3b-4ab2-b7f3-cb327afbe7ca', '-', '')), UNHEX(REPLACE('30a94d7f-bc9a-4bc5-9b04-d57ff52f5f28', '-', '')), 168, 144),

-- 대전 - 전라남도
        (UNHEX(REPLACE('05478710-db02-48d2-bd9a-a35f8bfd0056', '-', '')), UNHEX(REPLACE('30a94d7f-bc9a-4bc5-9b04-d57ff52f5f28', '-', '')), UNHEX(REPLACE('48d4bff9-bd31-48f4-a6f6-02f9b65d89b7', '-', '')), 225, 173),
        (UNHEX(REPLACE('2909bebc-da2d-464c-a555-ee0ada372179', '-', '')), UNHEX(REPLACE('48d4bff9-bd31-48f4-a6f6-02f9b65d89b7', '-', '')), UNHEX(REPLACE('30a94d7f-bc9a-4bc5-9b04-d57ff52f5f28', '-', '')), 225, 191),

-- 대전 - 대구
        (UNHEX(REPLACE('fa30df06-57fd-4675-91bf-353c5f9002de', '-', '')), UNHEX(REPLACE('30a94d7f-bc9a-4bc5-9b04-d57ff52f5f28', '-', '')), UNHEX(REPLACE('9b5ec8c5-03c2-4238-8533-57fa0a230dfe', '-', '')), 151, 139),
        (UNHEX(REPLACE('1d8968df-e1b4-433e-bcd7-74c504ed5b9c', '-', '')), UNHEX(REPLACE('9b5ec8c5-03c2-4238-8533-57fa0a230dfe', '-', '')), UNHEX(REPLACE('30a94d7f-bc9a-4bc5-9b04-d57ff52f5f28', '-', '')), 152, 128),

-- 대구 - 경상남도
        (UNHEX(REPLACE('72743704-9898-412d-a28b-240ff8b4619e', '-', '')), UNHEX(REPLACE('9b5ec8c5-03c2-4238-8533-57fa0a230dfe', '-', '')), UNHEX(REPLACE('3a8ea73f-bc29-4c9b-9531-e5c79fe75c18', '-', '')), 94, 104),
        (UNHEX(REPLACE('674dd294-b4a7-4f21-9ea5-df2bf0441e32', '-', '')), UNHEX(REPLACE('3a8ea73f-bc29-4c9b-9531-e5c79fe75c18', '-', '')), UNHEX(REPLACE('9b5ec8c5-03c2-4238-8533-57fa0a230dfe', '-', '')), 94, 92),

-- 대구 - 부산
        (UNHEX(REPLACE('a9f51ad4-68b9-4b0a-92a7-22473da0fa1b', '-', '')), UNHEX(REPLACE('9b5ec8c5-03c2-4238-8533-57fa0a230dfe', '-', '')), UNHEX(REPLACE('7f4d3776-e364-4cf3-9174-e56ffded57b5', '-', '')), 123, 121),
        (UNHEX(REPLACE('f2c9b732-6858-441b-8736-b1b16f40294d', '-', '')), UNHEX(REPLACE('7f4d3776-e364-4cf3-9174-e56ffded57b5', '-', '')), UNHEX(REPLACE('9b5ec8c5-03c2-4238-8533-57fa0a230dfe', '-', '')), 112, 104),

-- 대구 - 울산
        (UNHEX(REPLACE('091afd94-ce41-4ce5-8013-26fd45452817', '-', '')), UNHEX(REPLACE('9b5ec8c5-03c2-4238-8533-57fa0a230dfe', '-', '')), UNHEX(REPLACE('64b2c03f-e5ff-4e76-9ff4-07b8ed9e5e56', '-', '')), 121, 96),
        (UNHEX(REPLACE('b3627b08-7a63-4de7-a863-28fb420de888', '-', '')), UNHEX(REPLACE('64b2c03f-e5ff-4e76-9ff4-07b8ed9e5e56', '-', '')), UNHEX(REPLACE('9b5ec8c5-03c2-4238-8533-57fa0a230dfe', '-', '')), 109, 105),

-- 대구 - 경상북도
        (UNHEX(REPLACE('6e3a1dad-cf36-4e41-a774-971d3de8b3b7', '-', '')), UNHEX(REPLACE('9b5ec8c5-03c2-4238-8533-57fa0a230dfe', '-', '')), UNHEX(REPLACE('0de5607f-bd1b-4325-907d-9535066e4410', '-', '')), 110, 78),
        (UNHEX(REPLACE('4b059150-2449-4550-87c2-959387123ee7', '-', '')), UNHEX(REPLACE('0de5607f-bd1b-4325-907d-9535066e4410', '-', '')), UNHEX(REPLACE('9b5ec8c5-03c2-4238-8533-57fa0a230dfe', '-', '')), 106, 89),

-- 경상북도 - 경기남부
        (UNHEX(REPLACE('6cd555ff-56c4-4704-b79e-361ae3962d16', '-', '')), UNHEX(REPLACE('0de5607f-bd1b-4325-907d-9535066e4410', '-', '')), UNHEX(REPLACE('6d8a768d-9a74-40cc-9d0c-26eec6e3c111', '-', '')), 170, 138),
        (UNHEX(REPLACE('32371935-b690-434b-bef2-622c4c2fe087', '-', '')), UNHEX(REPLACE('6d8a768d-9a74-40cc-9d0c-26eec6e3c111', '-', '')), UNHEX(REPLACE('0de5607f-bd1b-4325-907d-9535066e4410', '-', '')), 169, 145);
