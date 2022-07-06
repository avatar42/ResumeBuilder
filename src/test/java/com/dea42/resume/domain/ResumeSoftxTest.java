package com.dea42.resume.domain;

import static org.assertj.core.api.Assertions.assertThat;

import com.dea42.resume.web.rest.TestUtil;
import org.junit.jupiter.api.Test;

class ResumeSoftxTest {

    @Test
    void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(ResumeSoftx.class);
        ResumeSoftx resumeSoftx1 = new ResumeSoftx();
        resumeSoftx1.setId(1L);
        ResumeSoftx resumeSoftx2 = new ResumeSoftx();
        resumeSoftx2.setId(resumeSoftx1.getId());
        assertThat(resumeSoftx1).isEqualTo(resumeSoftx2);
        resumeSoftx2.setId(2L);
        assertThat(resumeSoftx1).isNotEqualTo(resumeSoftx2);
        resumeSoftx1.setId(null);
        assertThat(resumeSoftx1).isNotEqualTo(resumeSoftx2);
    }
}
