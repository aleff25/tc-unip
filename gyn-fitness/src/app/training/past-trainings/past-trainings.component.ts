import {Component, OnInit, ViewChild, AfterViewInit, OnDestroy, OnChanges, SimpleChanges} from '@angular/core';
import {MatTableDataSource, MatSort, MatPaginator} from '@angular/material';
import {Subscription} from 'rxjs';

import {Exercise} from '../exercise.model';
import {TrainingService} from '../training.service';

@Component({
  selector: 'app-past-trainings',
  templateUrl: './past-trainings.component.html',
  styleUrls: ['./past-trainings.component.css']
})
export class PastTrainingsComponent implements OnInit, AfterViewInit, OnDestroy {
  displayedColumns = ['date', 'name', 'duration', 'calories', 'state'];
  dataSource = new MatTableDataSource<Exercise>();
  private exChangedSubscription: Subscription;

  @ViewChild(MatSort, {static: false}) sort: MatSort;
  @ViewChild(MatPaginator, {static: false}) paginator: MatPaginator;

  constructor(private trainingService: TrainingService) {
  }

  ngOnInit() {
    this.exChangedSubscription = this.trainingService.finishedExercisesChanged.subscribe(
      (exercise: Exercise) => {
        this.dataSource.data = [...this.dataSource.data, exercise];
        console.log(exercise, this.dataSource.data);
      }
    );
    this.exChangedSubscription = this.trainingService.fetchCompletedOrCancelledExercises()
      .subscribe((exercises: Exercise[]) => {
        this.dataSource.data = [...exercises];
      });
  }

  ngAfterViewInit() {
    this.dataSource.sort = this.sort;
    this.dataSource.paginator = this.paginator;
  }

  doFilter(filterValue: string) {
    this.dataSource.filter = filterValue.trim().toLowerCase();
  }

  ngOnDestroy() {
    if (this.exChangedSubscription) {
      this.exChangedSubscription.unsubscribe();
    }
  }
}
