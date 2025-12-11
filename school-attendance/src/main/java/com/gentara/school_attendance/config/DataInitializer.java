package com.gentara.school_attendance.config;

import com.gentara.school_attendance.enumz.AbsensiStatus;
import com.gentara.school_attendance.absensi.model.AbsensiEntity;
import com.gentara.school_attendance.absensi.repository.AbsensiRepo;
import com.gentara.school_attendance.enumz.UserRole;
import com.gentara.school_attendance.enumz.UserStatus;
import com.gentara.school_attendance.guru.model.GuruEntity;
import com.gentara.school_attendance.guru.repository.GuruRepo;
import com.gentara.school_attendance.kelas.model.KelasEntity;
import com.gentara.school_attendance.kelas.repository.KelasRepo;
import com.gentara.school_attendance.siswa.model.SiswaEntity;
import com.gentara.school_attendance.siswa.repository.SiswaRepo;
import com.gentara.school_attendance.user.model.UserEntity;
import com.gentara.school_attendance.user.repository.UserRepo;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Configuration;

import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@Configuration
@Slf4j
@AllArgsConstructor
public class DataInitializer implements CommandLineRunner {
    private KelasRepo kelasRepo;
    private SiswaRepo siswaRepo;
    private AbsensiRepo absensiRepo;
    private GuruRepo guruRepo;
    private UserRepo userRepo;

    @Override
    public void run(String... args) throws Exception {
        initTables();
    }
    private void initTables(){
        if (kelasRepo.count() != 0) return;
        if (siswaRepo.count() != 0) return;
        if (absensiRepo.count() != 0) return;
        if (guruRepo.count() != 0) return;
        if (userRepo.count() != 0) return;

        //kelas
        KelasEntity xIpa1 = new KelasEntity(UUID.randomUUID().toString(), "X IPA 1", "Budi", "2024/2025");
        KelasEntity xiIps2 = new KelasEntity(UUID.randomUUID().toString(), "XI IPS 2", "Siti", "2024/2025");
        KelasEntity xiiRpl1 = new KelasEntity(UUID.randomUUID().toString(), "XII RPL 1", "Kurniawan", "2024/2025");
        List<KelasEntity> initKelas = List.of(xIpa1,xiIps2,xiiRpl1);

        //siswa
        SiswaEntity johanLiebert = new SiswaEntity(UUID.randomUUID().toString(), "Johan Liebert", "20240101");
        SiswaEntity kenzoTenma = new SiswaEntity(UUID.randomUUID().toString(), "Kenzo Tenma", "20240102");
        SiswaEntity dieter = new SiswaEntity(UUID.randomUUID().toString(), "Dieter", "20240103");
        SiswaEntity annaLiebert = new SiswaEntity(UUID.randomUUID().toString(), "Anna Liebert", "20240104");
        List<SiswaEntity> initSiswa = List.of(johanLiebert, kenzoTenma, dieter, annaLiebert);

        //absensi
        AbsensiEntity siJohan = new AbsensiEntity(UUID.randomUUID().toString(), LocalDateTime.now(), AbsensiStatus.ALPHA);
        AbsensiEntity siTenma = new AbsensiEntity(UUID.randomUUID().toString(), LocalDateTime.now(), AbsensiStatus.IZIN);
        AbsensiEntity siDieter = new AbsensiEntity(UUID.randomUUID().toString(), LocalDateTime.now(), AbsensiStatus.HADIR);
        AbsensiEntity siAnna = new AbsensiEntity(UUID.randomUUID().toString(), LocalDateTime.now(), AbsensiStatus.SAKIT);
        List<AbsensiEntity> initAbsensi = List.of(siJohan, siTenma, siDieter, siAnna);

        //guru
        GuruEntity sakayanagi = new GuruEntity(UUID.randomUUID().toString(), "Sakayanagi", "19781215 200501 1 003","Matematika");
        GuruEntity ayanokouji = new GuruEntity(UUID.randomUUID().toString(), "Ayanokouji", "19890210 201002 2 004", "Sains");
        GuruEntity kiyotaka = new GuruEntity(UUID.randomUUID().toString(), "Kiyotaka", "19840621 200801 1 007", "B.Inggris");
        List<GuruEntity> initGuru = List.of(sakayanagi, ayanokouji, kiyotaka);

        //user
        UserEntity ayanokoujiUse = new UserEntity(UUID.randomUUID().toString(), "ayano", "ayano123", UserRole.GURU, UserStatus.AKTIF);
        UserEntity johanLiebertUse = new UserEntity(UUID.randomUUID().toString(), "johan", "johan123", UserRole.SISWA, UserStatus.AKTIF);
        List<UserEntity> initUser = List.of(ayanokoujiUse, johanLiebertUse);

        try {
            this.kelasRepo.saveAll(initKelas);
            this.siswaRepo.saveAll(initSiswa);
            this.absensiRepo.saveAll(initAbsensi);
            this.guruRepo.saveAll(initGuru);
            this.userRepo.saveAll(initUser);
            log.info("db initializer success");
        }catch (Exception e){
            log.error("db initializer failed, error {}",e.getMessage());
        }
    }
}
