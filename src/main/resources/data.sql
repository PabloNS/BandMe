INSERT INTO role (role_id, role) VALUES (1, 'ADMIN')
INSERT INTO role (role_id, role) VALUES (2, 'USER')

INSERT INTO band (id, name) VALUES (1, 'Ramones')
INSERT INTO band (id, name) VALUES (2, 'AC-DC')
INSERT INTO band (id, name) VALUES (3, 'Metallica')
INSERT INTO band (id, name) VALUES (4, 'Green Day')
INSERT INTO band (id, name) VALUES (5, 'blink-182')

INSERT INTO instrument(id, name, logo_image_name) VALUES (1, 'Electric Guitar', 'electric guitar.png')
INSERT INTO instrument(id, name, logo_image_name) VALUES (2, 'Bass', 'bass.png')
INSERT INTO instrument(id, name, logo_image_name) VALUES (3, 'Drums', 'drums.png')

INSERT INTO music_genre(id, name) VALUES (1, 'Trash Metal')
INSERT INTO music_genre(id, name) VALUES (2, 'Pop Punk')
INSERT INTO music_genre(id, name) VALUES (3, 'Punk Rock')
INSERT INTO music_genre(id, name) VALUES (4, 'Hard Rock')

INSERT INTO band_music_genres(band_id, music_genres_id) VALUES (1,3)
INSERT INTO band_music_genres(band_id, music_genres_id) VALUES (2,4)
INSERT INTO band_music_genres(band_id, music_genres_id) VALUES (3,1)
INSERT INTO band_music_genres(band_id, music_genres_id) VALUES (4,2)
INSERT INTO band_music_genres(band_id, music_genres_id) VALUES (5,2)