import {Component, Inject, OnInit} from '@angular/core';
import {MAT_DIALOG_DATA, MatDialogRef} from '@angular/material/dialog';

@Component({
    selector: 'app-custom-dialog',
    templateUrl: './custom-dialog.component.html',
    styleUrls: ['./custom-dialog.component.scss']
})
export class CustomDialogComponent implements OnInit {

    constructor(
        public dialogRef: MatDialogRef<CustomDialogComponent>,
        @Inject(MAT_DIALOG_DATA) public data: DialogData) {
    }

    public ngOnInit(): void {
    }

    onYesClick(): void {

        this.data.result = true;
        this.dialogRef.close(this.data);
    }

    onNoClick(): void {

        this.data.result = false;
        this.dialogRef.close(this.data);
    }
}

export interface DialogData {
    message?: string;
    result?: boolean;
    info?: boolean;
}
