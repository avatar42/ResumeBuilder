import { NgModule } from '@angular/core';
import { RouterModule } from '@angular/router';

@NgModule({
  imports: [
    RouterModule.forChild([
      {
        path: 'resume-hardware',
        data: { pageTitle: 'resumeApp.resumeHardware.home.title' },
        loadChildren: () => import('./resume-hardware/resume-hardware.module').then(m => m.ResumeHardwareModule),
      },
      {
        path: 'resume-hardx',
        data: { pageTitle: 'resumeApp.resumeHardx.home.title' },
        loadChildren: () => import('./resume-hardx/resume-hardx.module').then(m => m.ResumeHardxModule),
      },
      {
        path: 'resume-jobs',
        data: { pageTitle: 'resumeApp.resumeJobs.home.title' },
        loadChildren: () => import('./resume-jobs/resume-jobs.module').then(m => m.ResumeJobsModule),
      },
      {
        path: 'resume-respon-type',
        data: { pageTitle: 'resumeApp.resumeResponType.home.title' },
        loadChildren: () => import('./resume-respon-type/resume-respon-type.module').then(m => m.ResumeResponTypeModule),
      },
      {
        path: 'resume-software',
        data: { pageTitle: 'resumeApp.resumeSoftware.home.title' },
        loadChildren: () => import('./resume-software/resume-software.module').then(m => m.ResumeSoftwareModule),
      },
      {
        path: 'resume-softx',
        data: { pageTitle: 'resumeApp.resumeSoftx.home.title' },
        loadChildren: () => import('./resume-softx/resume-softx.module').then(m => m.ResumeSoftxModule),
      },
      {
        path: 'resume-summary',
        data: { pageTitle: 'resumeApp.resumeSummary.home.title' },
        loadChildren: () => import('./resume-summary/resume-summary.module').then(m => m.ResumeSummaryModule),
      },
      {
        path: 'resume-respon',
        data: { pageTitle: 'resumeApp.resumeRespon.home.title' },
        loadChildren: () => import('./resume-respon/resume-respon.module').then(m => m.ResumeResponModule),
      },
      /* jhipster-needle-add-entity-route - JHipster will add entity modules routes here */
    ]),
  ],
})
export class EntityRoutingModule {}
