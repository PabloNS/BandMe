INSERT INTO role (role_id, role) VALUES (1, 'ADMIN')
INSERT INTO role (role_id, role) VALUES (2, 'USER')

INSERT INTO band (id, name) VALUES (1, 'Ramones')
INSERT INTO band (id, name) VALUES (2, 'AC-DC')
INSERT INTO band (id, name) VALUES (3, 'Metallica')
INSERT INTO band (id, name) VALUES (4, 'Green Day')

INSERT INTO instrument(id, name) VALUES (1, 'Guitar')
INSERT INTO instrument(id, name) VALUES (2, 'Bass')
INSERT INTO instrument(id, name) VALUES (3,	'Drums')

INSERT INTO music_genre(id, name) VALUES (1, 'Trash Metal')
INSERT INTO music_genre(id, name) VALUES (2, 'Pop Punk')
INSERT INTO music_genre(id, name) VALUES (3, 'Punk Rock')
INSERT INTO music_genre(id, name) VALUES (4, 'Hard Rock')

INSERT INTO band_music_genres(band_id, music_genres_id) VALUES (1,3)
INSERT INTO band_music_genres(band_id, music_genres_id) VALUES (2,4)
INSERT INTO band_music_genres(band_id, music_genres_id) VALUES (3,1)
INSERT INTO band_music_genres(band_id, music_genres_id) VALUES (4,2)